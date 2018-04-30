import helpers.InputFileParser

import scala.io.StdIn.readLine

object ConferenceTrackManager {

  def main(args: Array[String]): Unit = {

    val fileName = readLine("Enter the input file path : ")

    val talkDetails = InputFileParser.parse(fileName)

    TrackBuilder.build(talkDetails) match {
      case Some(tracks) => TrackPrinter.print(tracks)
      case None => throw new Exception("Invalid Input")
    }
  }
}