import helpers.InputFileParser

object TrackManager {

  def main(args: Array[String]): Unit = {

    val talkDetails = InputFileParser.parse(args.head)

    TrackBuilder.build(talkDetails) match {
      case Some(tracks) => TrackPrinter.print(tracks)
      case None => throw new Exception("Invalid Input")
    }
  }
}