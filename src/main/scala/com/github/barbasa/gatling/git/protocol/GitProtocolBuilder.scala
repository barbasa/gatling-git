package com.github.barbasa.gatling.git.protocol

import io.gatling.core.config.GatlingConfiguration

object GitProtocolBuilder {
  implicit def toGitProtocol(builder: GitProtocolBuilder): GitProtocol =
    builder.build

  def apply(configuration: GatlingConfiguration): GitProtocolBuilder =
    GitProtocolBuilder(GitProtocol(configuration))
}

case class GitProtocolBuilder(gitProtocol: GitProtocol) {

  def build = gitProtocol

}
