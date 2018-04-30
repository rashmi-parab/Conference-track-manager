import java.time.LocalTime

import configs.TrackManagerConfig._
import models.{Talk, Track}
import scala.collection.mutable.ListBuffer

object TrackBuilder {

  def build(talkDetails: List[Talk]): Option[List[Track]] = {
    val noOfTracks = calculateNoOfTracks(talkDetails)
    val morningSessions: List[ListBuffer[Talk]] = List.fill(noOfTracks)(ListBuffer.empty)
    val eveningSessions: List[ListBuffer[Talk]] = List.fill(noOfTracks)(ListBuffer.empty)

    if (talkDetails.nonEmpty) {
      for (talk <- talkDetails) {
        if (!findAndInsertSlotIn(morningSessions, maxMinutesMorning, morningSessionStartTime, talk))
          findAndInsertSlotIn(eveningSessions, maxMinutesEvening.end, eveningSessionStartTime, talk)
      }
      Some((morningSessions zip eveningSessions).map { track => Track(track._1.toList, track._2.toList) })
    }
    else
      None
  }

  private def findAndInsertSlotIn(sessions: List[ListBuffer[Talk]], maxSessionTime: Int, sessionStartTime: LocalTime, talk: Talk): Boolean = {
    var slotFound = false
    for (session <- sessions if !slotFound) {
      if (session.map(_.duration).sum + talk.duration <= maxSessionTime) {
        val talkWithStartTime = session match {
          case talks if talks.isEmpty => talk.copy(startTime = sessionStartTime)
          case talks => talk.copy(startTime = talks.last.startTime.plusMinutes(talks.last.duration))
        }
        session += talkWithStartTime
        slotFound = true
      }
    }
    slotFound
  }

  private def calculateNoOfTracks(talkDetailsList: List[Talk]) = {
    Math.ceil(talkDetailsList.map(_.duration).sum / (maxMinutesMorning + maxMinutesEvening.max).toDouble).toInt
  }
}
