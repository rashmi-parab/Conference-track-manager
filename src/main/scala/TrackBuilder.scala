import java.time.LocalTime

import configs.TrackManagerConfig
import models.{Talk, Track}

import scala.collection.mutable.ListBuffer

object TrackBuilder {

  def build(talkDetailsList: List[Talk]): Option[List[Track]] = {
    val noOfTracks = calculateNoOfTracks(talkDetailsList)
    val morningSessions: List[ListBuffer[Talk]] = List.fill(noOfTracks)(ListBuffer.empty)
    val eveningSessions: List[ListBuffer[Talk]] = List.fill(noOfTracks)(ListBuffer.empty)

    if (talkDetailsList.nonEmpty) {
      for (talk <- talkDetailsList) {
        if (!findAndInsertSlotIn(morningSessions, TrackManagerConfig.maxMinutesMorning, TrackManagerConfig.morningSessionStartTime, talk))
          findAndInsertSlotIn(eveningSessions, TrackManagerConfig.maxMinutesEvening.end, TrackManagerConfig.eveningSessionStartTime, talk)
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
    Math.ceil(talkDetailsList.map(_.duration).sum / (TrackManagerConfig.maxMinutesMorning + TrackManagerConfig.maxMinutesEvening.max).toDouble).toInt
  }
}
