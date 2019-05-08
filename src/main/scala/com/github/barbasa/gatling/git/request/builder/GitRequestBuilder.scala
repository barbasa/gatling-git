package com.github.barbasa.gatling.git.request.builder

import com.github.barbasa.gatling.git.action.GitRequestActionBuilder
import com.github.barbasa.gatling.git.request._
import io.gatling.core.session.{Expression, Session}

object GitRequestBuilder {

  implicit def toActionBuilder(
      requestBuilder: GitRequestBuilder): GitRequestActionBuilder =
    new GitRequestActionBuilder(requestBuilder)

}

case class GitRequestBuilder(commandName: Expression[String],
                             url: Expression[String],
                             userExpr: Expression[String]) {

  // XXX This should probably return an Either if validation of the
  // parameters (i.e.: url and cmd) fails
  def buildWithSession(session: Session): Request = {
    val command = commandName(session).toOption.get.toLowerCase
    val u = url(session).toOption.get
    val user = userExpr(session).toOption.get.toLowerCase
    command match {
      case "clone" => Clone(u, user)
      case "pull"  => Pull(u, user)
      case "push"  => Push(u, user)
      case _       => InvalidRequest(u, user)
    }
  }
}
