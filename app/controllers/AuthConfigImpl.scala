package controllers

import jp.t2v.lab.play2.auth.{CookieIdContainer, AsyncIdContainer}
import play.api.mvc.RequestHeader
import play.api.mvc.Results._

import scala.concurrent.{Future, ExecutionContext}

trait AuthConfigImpl extends BaseAuthConfig {

def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect("/"))

def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect("/"))

def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect("/"))

override lazy val idContainer = AsyncIdContainer(new CookieIdContainer[Id])

}
