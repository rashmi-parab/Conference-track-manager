import models.Talk
import org.scalatest.{FlatSpec, Matchers}

class TrackBuilderSpec extends FlatSpec with Matchers {

  "TrackBuilder" should "compute tracks from given talk details" in {

    val talkDetails = List(
      Talk("Writing Fast Tests Against Enterprise Rails 60min", 60),
      Talk("Overdoing it in Python 45min", 45),
      Talk("Lua for the Masses 30min", 30),
      Talk("Ruby Errors from Mismatched Gem Versions 45min", 45),
      Talk("Common Ruby Errors 45min", 45),
      Talk("Communicating Over Distance 60min", 60),
      Talk("Accounting-Driven Development 45min", 45),
      Talk("Woah 30min", 30),
      Talk("Sit Down and Write 30min", 30),
      Talk("Pair Programming vs Noise 45min", 45),
      Talk("Rails Magic 60min", 60),
      Talk("Ruby on Rails: Why We Should Move On 60min", 60),
      Talk("Clojure Ate Scala (on my project) 45min", 45),
      Talk("Programming in the Boondocks of Seattle 30min", 30),
      Talk("Ruby vs. Clojure for Back-End Development 30min", 30),
      Talk("Ruby on Rails Legacy App Maintenance 60min", 60),
      Talk("A World Without HackerNews 30min", 30),
      Talk("User Interface CSS in Rails Apps 30min", 30),
      Talk("Rails for Python Developers lightning", 5)
    )

    val tracks = TrackBuilder.build(talkDetails).get

    tracks.size shouldBe 2

    tracks(0).morningSession shouldBe List(
      Talk("Writing Fast Tests Against Enterprise Rails 60min", 60),
      Talk("Overdoing it in Python 45min", 45),
      Talk("Lua for the Masses 30min", 30),
      Talk("Ruby Errors from Mismatched Gem Versions 45min", 45))

    tracks(0).eveningSession shouldBe List(
      Talk("Sit Down and Write 30min", 30),
      Talk("Pair Programming vs Noise 45min", 45),
      Talk("Rails Magic 60min", 60),
      Talk("Ruby on Rails: Why We Should Move On 60min", 60),
      Talk("Programming in the Boondocks of Seattle 30min", 30),
      Talk("Rails for Python Developers lightning", 5))

    tracks(1).morningSession shouldBe List(
      Talk("Common Ruby Errors 45min", 45),
      Talk("Communicating Over Distance 60min", 60),
      Talk("Accounting-Driven Development 45min", 45),
      Talk("Woah 30min", 30))

    tracks(1).eveningSession shouldBe List(
      Talk("Clojure Ate Scala (on my project) 45min", 45),
      Talk("Ruby vs. Clojure for Back-End Development 30min", 30),
      Talk("Ruby on Rails Legacy App Maintenance 60min", 60),
      Talk("A World Without HackerNews 30min", 30),
      Talk("User Interface CSS in Rails Apps 30min", 30))
  }

  "TrackBuilder" should "return empty list given empty list of talkDetails" in {
    val tracks = TrackBuilder.build(List.empty)

    tracks shouldBe None
  }
}
