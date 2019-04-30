package com.github.barbasa.gatling.git

import com.github.barbasa.gatling.git.protocol.GitProtocolBuilder
import io.gatling.core.config.GatlingConfiguration

trait GitDsl {
  def git(implicit configuration: GatlingConfiguration) =
    GitProtocolBuilder(configuration)
}
