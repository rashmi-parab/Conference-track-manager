package helpers

import org.scalatest.{FlatSpec, Matchers}
import configs.TrackManagerConfig.maxMinutesEvening

class InputFileParserSpec extends FlatSpec with Matchers {

  "InputParser" should "parse the input file into a list of talkDescription and duration" in {

    val talkDetails = InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/ValidConferenceTalkDetails")

    talkDetails.length shouldBe 19
    talkDetails.head.title shouldBe "Writing Fast Tests Against Enterprise Rails 60min"
    talkDetails.head.duration shouldBe 60

    talkDetails.last.title shouldBe "Rails for Python Developers lightning"
    talkDetails.last.duration shouldBe 5
  }

  "InputParser" should "parse successfully if no lighting talks" in {
    val talkDetails = InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/NoLightingTalkDetails")

    talkDetails.length shouldBe 5
    talkDetails.head.title shouldBe "Writing Fast Tests Against Enterprise Rails 60min"
    talkDetails.head.duration shouldBe 60
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

  "InputParser" should "throw exception when talk title contains numeric value" in {
    val error = intercept[Exception] {
      InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/InvalidTitleTalkDetails")
    }

    error.getMessage.contains("Talk title sould not have numbers or Invalid talk duration") shouldBe true
  }

  "InputParser" should "throw exception when talk duration is invalid" in {
    val error = intercept[Exception] {
      InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/InvalidDurationTalkDetails")
    }

    error.getMessage.contains("Talk title sould not have numbers or Invalid talk duration") shouldBe true
  }

  "InputParser" should "throw exception when talk duration is greater than max session slot" in {
    val error = intercept[Exception] {
      InputFileParser.parse("/Users/mangeshphuge/ConferenceTrackManagerScalaRP/src/test/testData/DurationGreaterThanMaxLimit")
    }

    error.getMessage.contains(s"Duration should not be greater than $maxMinutesEvening Minutes") shouldBe true
  }
}