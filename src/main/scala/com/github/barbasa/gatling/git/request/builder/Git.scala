package com.github.barbasa.gatling.git.request.builder

import io.gatling.core.session.{Expression, StaticStringExpression}

case object Git {
  def clone(url: Expression[String]): GitRequestBuilder =
    new GitRequestBuilder(StaticStringExpression("clone"),
                          url,
                          StaticStringExpression("anyUser"))

  def pull(url: Expression[String]): GitRequestBuilder =
    new GitRequestBuilder(StaticStringExpression("pull"),
                          url,
                          StaticStringExpression("anyUser"))

  def push(url: Expression[String]): GitRequestBuilder =
    new GitRequestBuilder(StaticStringExpression("push"),
                          url,
                          StaticStringExpression("anyUser"))
}
