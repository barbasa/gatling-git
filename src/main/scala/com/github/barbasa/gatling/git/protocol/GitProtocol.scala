package com.github.barbasa.gatling.git.protocol

import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.Protocol

object GitProtocol {
  def apply(configuration: GatlingConfiguration): GitProtocol = GitProtocol()
}

case class GitProtocol() extends Protocol
