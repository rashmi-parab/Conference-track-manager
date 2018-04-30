import java.time.LocalTime

import configs.TrackManagerConfig.networkSessionStartTime
import helpers.TimeFormatter
import models.Track

object TrackPrinter {
  def print(tracks: List[Track]) = {
    for (t <- tracks.indices) {
      println()
      println(s"Track $t :")

      for (morningSession <- tracks(t).morningSession) {
        println(s"${TimeFormatter.format(morningSession.startTime)} ${morningSession.title}")
      }

      println(s"${TimeFormatter.format(LocalTime.of(12, 0))} Lunch")

      for (eveningSession <- tracks(t).eveningSession) {
        println(s"${TimeFormatter.format(eveningSession.startTime)} ${eveningSession.title}")
      }
      println(s"${TimeFormatter.format(calculateNetworkSessionStartTime(t))} Networking Event")
    }

    def calculateNetworkSessionStartTime(trackNo: Int): LocalTime = {
      val lastTalk = tracks(trackNo).eveningSession.last

      lastTalk.title match {
        case "Lunch" => LocalTime.of(networkSessionStartTime.start, 0)
        case _ =>
          val lastTalkEndTime = lastTalk.startTime.plusMinutes(lastTalk.duration)
          if (lastTalkEndTime.getHour < networkSessionStartTime.start) LocalTime.of(networkSessionStartTime.start, 0)
          else lastTalkEndTime
      }
    }
  }
}