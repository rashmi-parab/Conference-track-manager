package helpers

import configs.TrackManagerConfig._
import models.Talk

import scala.io.Source

object InputFileParser {

  def parse(filePath: String): List[Talk] = {
    val bufferedSource = Source.fromFile(filePath)

    try {
      if (bufferedSource.nonEmpty) {
        val allTalks = bufferedSource.getLines.toList.map { talkDetails =>
          withValidTalkDetails(talkDetails) { (_ , duration) =>
            Talk(talkDetails, duration)
          }
        }

        val lightningTalks = allTalks.filter(talk => talk.duration == 5)
        val otherTalks = allTalks diff lightningTalks

        otherTalks ++ lightningTalks
      }
      else {
        throw new Exception("File is empty")
      }
    }
    catch {
      case ex: Exception => throw new Exception(s"Invalid input data : ${ex.getMessage}", ex)
    }
    finally {
      bufferedSource.close
    }
  }

  private def withValidTalkDetails(talkDetails: String)(f: (String, Int) => Talk): Talk = {
    val title = talkDetails.substring(0, talkDetails.lastIndexOf(" "))
    val duration = talkDetails.split(" ").last

    if (duration.matches(s"(\\d+min)|lightning") && !title.exists(Character.isDigit)) {
      val durationAsInt = duration match {
        case "lightning" => 5
        case _ => {
          val d = duration.substring(0, duration.length - 3).toInt
          if (d <= maxMinutesEvening) d else throw new Exception(s"Duration should not be greater than $maxMinutesEvening Minutes")
        }
      }
      f(title, durationAsInt)
    }
    else throw new Exception("Talk title sould not have numbers or Invalid talk duration")
  }
}