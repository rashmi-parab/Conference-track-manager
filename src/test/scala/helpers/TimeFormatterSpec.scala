package helpers

import java.time.{DateTimeException, LocalTime}

import org.scalatest.{FlatSpec, Matchers}

class TimeFormatterSpec extends FlatSpec with Matchers {

  "TimeFormatter" should "format a given LocalTime to 12hours format" in {

    TimeFormatter.format(LocalTime.of(0, 0)) shouldBe "12:00AM"
    TimeFormatter.format(LocalTime.of(12, 0)) shouldBe "12:00PM"

    TimeFormatter.format(LocalTime.of(11, 0)) shouldBe "11:00AM"
    TimeFormatter.format(LocalTime.of(23, 0)) shouldBe "11:00PM"

    TimeFormatter.format(LocalTime.of(1, 0)) shouldBe "01:00AM"
    TimeFormatter.format(LocalTime.of(13, 0)) shouldBe "01:00PM"

    TimeFormatter.format(LocalTime.of(10, 53)) shouldBe "10:53AM"
    TimeFormatter.format(LocalTime.of(16, 30)) shouldBe "04:30PM"
  }

  "TimeFormatter" should "DateTimeException if invalid Hour" in {

    val error = intercept[DateTimeException] {
      TimeFormatter.format(LocalTime.of(24, 0))
    }
    error.getMessage shouldBe "Invalid value for HourOfDay (valid values 0 - 23): 24"
  }

  "TimeFormatter" should "DateTimeException if invalid Minutes" in {

    val error = intercept[DateTimeException] {
      TimeFormatter.format(LocalTime.of(10, 70))
    }
    error.getMessage shouldBe "Invalid value for MinuteOfHour (valid values 0 - 59): 70"
  }
}
