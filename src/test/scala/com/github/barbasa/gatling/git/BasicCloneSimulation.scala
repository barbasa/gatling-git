package com.github.barbasa.gatling.git

import com.github.barbasa.gatling.git.protocol.GitProtocol
import com.github.barbasa.gatling.git.request.builder.Git
import io.gatling.core.Predef.{exec, _}
import io.gatling.core.structure.ScenarioBuilder

class BasicCloneSimulation extends Simulation {

  val gitProtocol = GitProtocol()

  println("===>>>")

  val scenario1: ScenarioBuilder = scenario("Git Clone").repeat(2)(
    exec(Git("ciccio").clone("http://localhost:8081/test"))
  )

  setUp(scenario1.inject(atOnceUsers(3)))
    .protocols(gitProtocol)
}
