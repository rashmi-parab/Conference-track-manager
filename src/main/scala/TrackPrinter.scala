import java.time.LocalTime

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
      println(s"${TimeFormatter.format(networkingStartTime(t))} Networking Event")
    }

    def networkingStartTime(trackNo: Int) = {
      val lastTalk = tracks(trackNo).eveningSession.last
      lastTalk.startTime.plusMinutes(lastTalk.duration)
    }
  }
}