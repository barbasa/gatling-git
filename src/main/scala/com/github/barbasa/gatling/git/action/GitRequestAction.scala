package com.github.barbasa.gatling.git.action

import com.github.barbasa.gatling.git.request.{OK, Request}
import io.gatling.commons.util.Clock
import io.gatling.commons.validation.{Failure, Success, Validation}
import io.gatling.core.CoreComponents
import io.gatling.core.action.{Action, ExitableAction, RequestAction}
import io.gatling.core.session.{Expression, Session}
import io.gatling.core.stats.StatsEngine
import io.gatling.core.util.NameGen

class GitRequestAction(coreComponents: CoreComponents,
                       gitRequest: Request,
                       val next: Action)
    extends RequestAction
    with ExitableAction
    with NameGen {
  override def requestName: Expression[String] = { session =>
    Success(gitRequest.name)
  }

  override def sendRequest(requestName: String,
                           session: Session): Validation[Unit] = {

    val start = clock.nowMillis

    val response = gitRequest.send
    statsEngine.logResponse(session,
                            requestName,
                            start,
                            clock.nowMillis,
                            Request.gatlingStatusFromGit(response),
                            None,
                            None)

    response.status match {
      case OK => Success("dsdss")
      case _  => Failure("Failed REquest!")
    }
  }

  override def statsEngine: StatsEngine = coreComponents.statsEngine

  override def clock: Clock = coreComponents.clock

  override def name: String = genName("GitRequest")
}
