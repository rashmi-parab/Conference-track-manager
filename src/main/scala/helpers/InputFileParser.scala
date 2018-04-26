package helpers

import scala.io.Source

object InputFileParser {

  def parse(filePath: String): List[(String, Int)] = {
    val bufferedSource = Source.fromFile(filePath)

    try {
      if (bufferedSource.isEmpty)
        throw new Exception("File is empty")
      else {
        bufferedSource.getLines.toList.map { talkTitle =>
          val talkDuration = talkTitle.split(" ").last match {
            case "lightning" => 5
            case duration => duration.substring(0, 2).toInt
          }
          (talkTitle, talkDuration)
        }
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
