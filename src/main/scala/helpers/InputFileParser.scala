package helpers

import configs.TrackManagerConfig
import models.Talk

import scala.io.Source

object InputFileParser {

  def parse(filePath: String): List[Talk] = {
    val bufferedSource = Source.fromFile(filePath)

    try {
      if (bufferedSource.nonEmpty) {
        val allTalks = bufferedSource.getLines.toList.map { talkTitle =>
          talkTitle.split(" ").last match {
            case "lightning" => Talk(talkTitle, TrackManagerConfig.LightningTalkDuration)
            case duration => Talk(talkTitle, duration.substring(0, 2).toInt)
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
}
