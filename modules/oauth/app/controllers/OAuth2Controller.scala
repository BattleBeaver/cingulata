package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.OAuthDataHandler

import scalaoauth2.provider._

class OAuth2Controller @Inject()(oAuthDataHandler: OAuthDataHandler) extends Controller with OAuth2Provider {
  override val tokenEndpoint = new OAuthTokenEndPoint()

  def accessToken = Action.async { implicit request =>
    issueAccessToken(oAuthDataHandler)
  }
}
