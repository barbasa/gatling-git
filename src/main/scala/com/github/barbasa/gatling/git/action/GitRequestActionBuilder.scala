package com.github.barbasa.gatling.git.action

import com.github.barbasa.gatling.git.request.builder.GitRequestBuilder
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.structure.ScenarioContext

class GitRequestActionBuilder(requestBuilder: GitRequestBuilder)
    extends ActionBuilder {
  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx.coreComponents

    val gitRequest =
      requestBuilder.build(coreComponents,
                           requestBuilder.commandName,
                           Map.empty)
    new GitRequestAction(coreComponents, gitRequest, next)
  }

}
