package models

import java.time.LocalTime

case class Talk(title: String, duration: Int, startTime : LocalTime = LocalTime.of(0,0))

