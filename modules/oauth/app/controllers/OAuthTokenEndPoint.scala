package controllers

import scalaoauth2.provider.{OAuthGrantType, Password, TokenEndpoint}

class OAuthTokenEndPoint extends TokenEndpoint {
  val passwordNoCred = new Password() {
    override def clientCredentialRequired = false
  }

  override val handlers = Map(
    OAuthGrantType.PASSWORD -> passwordNoCred
  )
}
