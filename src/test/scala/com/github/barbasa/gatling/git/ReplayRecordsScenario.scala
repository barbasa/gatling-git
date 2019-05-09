package com.github.barbasa.gatling.git

import com.github.barbasa.gatling.git.protocol.GitProtocol
import com.github.barbasa.gatling.git.request.builder.GitRequestBuilder
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import scala.concurrent.duration._

class ReplayRecordsScenario extends Simulation {

  val gitProtocol = GitProtocol()

  val feeder = csv("data/requests.csv").circular

  val replayCallsScenario: ScenarioBuilder =
    scenario("Git commands")
      .repeat(10000) {
        feed(feeder)
          .exec(
            new GitRequestBuilder("${cmd}",
                                  "${url}",
                                  "${user}"))
      }
//      .pause("${pause}")

  setUp(
    replayCallsScenario.inject(
      nothingFor(4 seconds),
      atOnceUsers(10),
      rampUsers(10) during (5 seconds),
      constantUsersPerSec(20) during (15 seconds),
      constantUsersPerSec(20) during (15 seconds) randomized
    ))
    .protocols(gitProtocol)
    .maxDuration(60 seconds)
  // XXX Add tear down to cleanup the files created during the simulation
}
