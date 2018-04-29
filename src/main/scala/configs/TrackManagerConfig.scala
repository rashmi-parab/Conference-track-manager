package configs

import java.time.LocalTime

object TrackManagerConfig {
  val LightningTalkDuration = 5
  val maxMinutesMorning = 180
  val maxMinutesEvening = Range(180, 240)
  val morningSessionStartTime = LocalTime.of(9, 0) //24hours time
  val eveningSessionStartTime = LocalTime.of(13, 0) //24hours time
}
