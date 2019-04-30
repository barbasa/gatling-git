package com.github.barbasa.gatling.git.request.builder

import com.github.barbasa.gatling.git.action.{GitRequestActionBuilder}
import com.github.barbasa.gatling.git.request.{Clone, Request}
import io.gatling.core.CoreComponents

object GitRequestBuilder {

  implicit def toActionBuilder(
      requestBuilder: GitRequestBuilder): GitRequestActionBuilder =
    new GitRequestActionBuilder(requestBuilder)

}

case class GitRequestBuilder(commandName: String) {

  def build(coreComponents: CoreComponents,
            commandName: String,
            attributes: Map[String, String]): Request = {
    //XXX The correct command has to be built here...for now let's build a Clone
    Clone()
  }
}
