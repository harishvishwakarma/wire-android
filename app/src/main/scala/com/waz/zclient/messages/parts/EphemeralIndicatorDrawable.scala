/**
 * Wire
 * Copyright (C) 2018 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.waz.zclient.messages.parts

import android.content.Context
import android.graphics._
import android.graphics.drawable.Drawable
import com.waz.ZLog.ImplicitTag._
import com.waz.model.MessageId
import com.waz.service.ZMessaging
import com.waz.threading.Threading
import com.waz.utils.events.{ClockSignal, EventContext, Signal}
import com.waz.utils.returning
import com.waz.zclient.utils.ContextUtils._
import com.waz.zclient.{Injectable, Injector}
import com.waz.zclient.R

class EphemeralIndicatorDrawable(implicit injector: Injector, ctx: Context, ec: EventContext) extends Drawable with Injectable {

  private val zms = inject[Signal[ZMessaging]]
  private val msgId = Signal[MessageId]()

  private val paint = returning(new Paint(Paint.ANTI_ALIAS_FLAG))(_.setColor(getColor(R.color.light_graphite)))
  private val bgPaint = returning(new Paint(Paint.ANTI_ALIAS_FLAG))(_.setColor(getColor(R.color.light_graphite_16)))

  private val msg = for {
    z <- zms
    id <- msgId
    m <- z.messagesStorage.signal(id)
  } yield m

  private val timerAngle = msg
    .map { m => (m.ephemeral, m.expired, m.expiryTime) }  // optimisation to ignore unrelated changes
    .flatMap {
    case (ephemeral, expired, expiryTime) =>
      if (expired) Signal const 0
      else expiryTime.fold(Signal const 360) { time =>
        val interval = ephemeral.get / 360
        ClockSignal(interval) map { now =>
          val remaining = time.toEpochMilli - now.toEpochMilli
          (remaining * 360f / ephemeral.get.toMillis).toInt max 0 min 360
        }
      }
  }

  private val state = for {
    ephemeral <- msg.map(_.isEphemeral)
    angle <- timerAngle
  } yield (ephemeral, angle)

  state.on(Threading.Ui) { _ => invalidateSelf() }

  override def setColorFilter(colorFilter: ColorFilter): Unit = paint.setColorFilter(colorFilter)

  override def setAlpha(alpha: Int): Unit = paint.setAlpha(alpha)

  override def getOpacity: Int = PixelFormat.TRANSLUCENT

  override def draw(canvas: Canvas): Unit = state.currentValue match {
    case Some((true, angle)) if canvas != null && canvas.getHeight > 0 =>
      canvas.drawArc(0, 0, canvas.getWidth, canvas.getHeight, 0, 360, true, bgPaint)
      canvas.drawArc(0, 0, canvas.getWidth, canvas.getHeight, -90, -angle, true, paint)
    case _ => // nothing to draw, not ephemeral or not loaded
  }

  def setMessage(id: MessageId): Unit = msgId ! id
}
