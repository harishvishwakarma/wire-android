/**
 * Wire
 * Copyright (C) 2017 Wire Swiss GmbH
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
package com.waz.zclient.cursor

import com.waz.zclient.ui.R

case class CursorMenuItem(name: String, glyphResId: Int, timedGlyphResId: Int, resId: Int, resTooltip: Int)

object CursorMenuItem {
  val VideoMessage  = CursorMenuItem("VIDEO_MESSAGE", R.string.glyph__record, R.string.glyph__record_timed, R.id.cursor_menu_item_video, R.string.tooltip_record)
  val Camera        = CursorMenuItem("CAMERA", R.string.glyph__camera, R.string.glyph__camera_timed, R.id.cursor_menu_item_camera, R.string.tooltip_camera)
  val Sketch        = CursorMenuItem("SKETCH", R.string.glyph__paint, R.string.glyph__sketch_timed, R.id.cursor_menu_item_draw, R.string.tooltip_sketch)
  val File          = CursorMenuItem("FILE", R.string.glyph__attachment, R.string.glyph__attachment_timed, R.id.cursor_menu_item_file, R.string.tooltip_file)
  val Gif           = CursorMenuItem("GIF", R.string.glyph__gif, R.string.glyph__gif_timed, R.id.cursor_menu_item_gif, R.string.tooltip_gif)
  val AudioMessage  = CursorMenuItem("AUDIO_MESSAGE", R.string.glyph__microphone_on, R.string.glyph__microphone_on_timed, R.id.cursor_menu_item_audio_message, R.string.tooltip_audio_message)
  val More          = CursorMenuItem("MORE", R.string.glyph__more, R.string.glyph__more, R.id.cursor_menu_item_more, R.string.tooltip_more)
  val Less          = CursorMenuItem("LESS", R.string.glyph__more, R.string.glyph__more, R.id.cursor_menu_item_less, R.string.tooltip_more)
  val Location      = CursorMenuItem("LOCATION", R.string.glyph__location, R.string.glyph__location_timed, R.id.cursor_menu_item_location, R.string.tooltip_location)
  val Emoji         = CursorMenuItem("EMOJI", R.string.glyph__emoji, R.string.glyph__emoji_timed, R.id.cursor_menu_item_emoji, R.string.tooltip_emoji)
  val Keyboard      = CursorMenuItem("KEYBOARD", R.string.glyph__keyboard, R.string.glyph__keyboard, R.id.cursor_menu_item_keyboard, R.string.tooltip_emoji)
  val Ping          = CursorMenuItem("PING", R.string.glyph__ping, R.string.glyph__ping_timed, R.id.cursor_menu_item_ping, R.string.tooltip_ping)
  val Send          = CursorMenuItem("SEND", R.string.glyph__send, R.string.glyph__send, R.id.cursor_menu_item_send, R.string.empty_string)
  val Dummy         = CursorMenuItem("", R.string.empty_string, R.string.empty_string, R.id.cursor_menu_item_dummy, R.string.empty_string)
}
