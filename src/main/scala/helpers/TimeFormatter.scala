package helpers

import java.text.SimpleDateFormat
import java.time.LocalTime

object TimeFormatter {
  def format(time: LocalTime) : String = {
    val sdf = new SimpleDateFormat("HH:mm")
    val dateObj = sdf.parse(time.toString)
    new SimpleDateFormat("hh:mmaa").format(dateObj)
  }
}