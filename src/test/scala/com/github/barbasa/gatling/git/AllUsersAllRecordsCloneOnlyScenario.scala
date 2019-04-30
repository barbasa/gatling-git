package com.github.barbasa.gatling.git

import com.github.barbasa.gatling.git.protocol.GitProtocol
import com.github.barbasa.gatling.git.request.builder.Git
import io.gatling.core.Predef.{exec, _}
import io.gatling.core.structure.ScenarioBuilder

class AllUsersAllRecordsCloneOnlyScenario extends Simulation {

  val gitProtocol = GitProtocol()

  val feeder = Array(
    Map("repo" -> "test"),
    Map("repo" -> "test1"),
    Map("repo" -> "cose")
  ).readRecords

  val allUsersAllCallsScenario: ScenarioBuilder =
    scenario("Git Clone").foreach(feeder, "record") {
      exec(flattenMapIntoAttributes("${record}"))
        .exec(Git.clone("http://localhost:8081/${repo}"))
    }

  setUp(allUsersAllCallsScenario.inject(atOnceUsers(3)))
    .protocols(gitProtocol)
}
