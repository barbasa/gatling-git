package com.github.barbasa.gatling.git

import com.github.barbasa.gatling.git.protocol.GitProtocol
import com.github.barbasa.gatling.git.request.builder.GitRequestBuilder
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.{FiniteDuration, MINUTES, SECONDS}

class ReplayRecordsScenario extends Simulation {

  val gitProtocol = GitProtocol()

  val feeder = csv("data/requests.csv").circular

  val replayCallsScenario: ScenarioBuilder =
    scenario("Git commands")
      .repeat(10000) {
        feed(feeder)
          .exec(
            new GitRequestBuilder("${cmd}",
                                  "http://localhost:8081/${repo}",
                                  "${user}"))
      }
//      .pause("${pause}")

  setUp(replayCallsScenario.inject(atOnceUsers(3)))
    .protocols(gitProtocol)
    .maxDuration(new FiniteDuration(2, SECONDS))
  // XXX Add tear down to cleanup the files created during the simulation
}
