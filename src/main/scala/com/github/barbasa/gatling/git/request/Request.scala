package com.github.barbasa.gatling.git.request
import java.io.File

import io.gatling.commons.stats.{OK => GatlingOK}
import io.gatling.commons.stats.{KO => GatlingFail}
import io.gatling.commons.stats.Status
import org.eclipse.jgit.api.CloneCommand

sealed trait Request {
  def name: String
  def send: Response

}

object Request {
  def gatlingStatusFromGit(response: Response): Status = {
    response.status match {
      case OK   => GatlingOK
      case Fail => GatlingFail
    }
  }
}

case class Clone() extends Request {
  val name = "clone"
  def send(): Response = {
    val rnd = Math.random()
    val cloneCommand = new CloneCommand()
      .setURI("http://localhost:8081/test")
      .setDirectory(new File(s"/tmp/test-$rnd"))
    try {
      cloneCommand.call()
      Response(OK)
    } catch {
      case _: Throwable => {
        Response(Fail)
      }
    }

  }
}

case class Response(status: ResponseStatus)

sealed trait ResponseStatus
case object OK extends ResponseStatus
case object Fail extends ResponseStatus
