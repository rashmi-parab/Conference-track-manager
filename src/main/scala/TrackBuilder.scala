import configs.TrackManagerConfig
import models.{Talk, Track}

import scala.collection.mutable.ListBuffer

object TrackBuilder {

  def build(talkDetailsList: List[Talk]): List[Track] = {
    val noOfTracks = calculateNoOfTracks(talkDetailsList)
    val morningSessions: List[ListBuffer[Talk]] = List.fill(noOfTracks)(ListBuffer.empty)
    val eveningSessions: List[ListBuffer[Talk]] = List.fill(noOfTracks)(ListBuffer.empty)

    def findSlotIn(sessions: List[ListBuffer[Talk]], maxSessionTime: Int, talk: Talk): Boolean = {
      var slotFound = false
      for (t <- sessions.indices if !slotFound) {
        if (sessions(t).map(_.duration).sum + talk.duration <= maxSessionTime) {
          sessions(t) += talk
          slotFound = true
        }
      }
      slotFound
    }

    for (i <- talkDetailsList.indices) {
      if (!findSlotIn(morningSessions, TrackManagerConfig.maxMinutesMorning, talkDetailsList(i)))
        findSlotIn(eveningSessions, TrackManagerConfig.maxMinutesEvening.max, talkDetailsList(i))
    }

    (morningSessions zip eveningSessions).map { track =>
      Track(track._1.toList, track._2.toList)
    }
  }

  private def calculateNoOfTracks(talkDetailsList: List[Talk]) = {
    Math.ceil(talkDetailsList.map(_.duration).sum / (TrackManagerConfig.maxMinutesMorning + TrackManagerConfig.maxMinutesEvening.max).toDouble).toInt
  }
}
