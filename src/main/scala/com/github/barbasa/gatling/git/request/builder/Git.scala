package com.github.barbasa.gatling.git.request.builder

import io.gatling.core.session.Expression

case class Git(requestName: Expression[String]) {
  def clone(url: Expression[String]): GitRequestBuilder =
    new GitRequestBuilder("clone")
}
