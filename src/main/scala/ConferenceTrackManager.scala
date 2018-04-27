import helpers.InputFileParser

object ConferenceTrackManager {

  def main(args: Array[String]): Unit = {

    val fileName = scala.io.StdIn.readLine("Enter the input file path : ")

    val talkDetailsList = InputFileParser.parse(fileName)

    val tracks = TrackBuilder.build(talkDetailsList)
  }
}
