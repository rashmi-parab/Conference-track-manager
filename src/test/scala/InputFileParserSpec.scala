import helpers.InputFileParser
import org.scalatest.{FlatSpec, Matchers}

class InputFileParserSpec extends FlatSpec with Matchers {

  "InputParser" should "parse the input file into a list of talkDescription and duration" in {

    val talkDetails = InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/ValidConferenceTalkDetails")

    talkDetails.length shouldBe 19
    talkDetails.head._1 shouldBe "Writing Fast Tests Against Enterprise Rails 60min"
    talkDetails.head._2 shouldBe 60
    talkDetails(5)._1 shouldBe "Rails for Python Developers lightning"
    talkDetails(5)._2 shouldBe 5
  }

  "InputParser" should "throw exception when Input file contents are empty" in {

    val error = intercept[Exception] {
      InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/EmptyConferenceTalkDetails")
    }

    error.getMessage shouldBe "Invalid input data : File is empty"
  }

  "InputParser" should "throw exception when file path is invalid or not found" in {

    val error = intercept[Exception] {
      InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/InvalidFileName")
    }

    error.getMessage.contains("No such file or directory") shouldBe true
  }

  "InputParser" should "throw exception when talk duration not specified" in {

    val error = intercept[Exception] {
      InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/MissingDurationTalkDetails")
    }

    error.getMessage.contains("Invalid input data") shouldBe true
  }
}
