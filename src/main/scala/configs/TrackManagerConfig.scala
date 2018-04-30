package configs

import java.time.LocalTime

object TrackManagerConfig {
  val LightningTalkDuration = 5
  val maxMinutesMorning = 180
  val maxMinutesEvening = 240
  val networkSessionStartTime = Range(4,5)
  val morningSessionStartTime = LocalTime.of(9, 0) //24hours time
  val eveningSessionStartTime = LocalTime.of(13, 0) //24hours time
}
