package controllers

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

import scalaoauth2.provider._

class OAuth2Controller extends Controller with OAuth2Provider {
  override val tokenEndpoint = new OAuthTokenEndPoint()

  def accessToken = Action.async { implicit request =>
    issueAccessToken(new OAuthDataHandler())
  }
}
