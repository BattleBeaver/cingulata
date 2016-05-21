package services

import com.google.inject.Inject
import models.User

import scala.concurrent.{ExecutionContext, Future}

import scalaoauth2.provider.{AccessToken, AuthInfo, AuthorizationRequest, DataHandler}

class OAuthDataHandler @Inject()(userService: UserService)(implicit  val executionContext: ExecutionContext) extends DataHandler[User] {
  def validateClient(request: AuthorizationRequest): Future[Boolean] = {
    request.clientCredential match {
      case Some(credential) => userService.exists(credential.clientId, credential.clientSecret.getOrElse(throw new Exception("No password provided")))
      case _ => Future(false)
    }
  }

  def findUser(request: AuthorizationRequest): Future[Option[User]] = {
    request.clientCredential match {
      case Some(credential) => userService.find(credential.clientId, credential.clientSecret.getOrElse(throw new Exception("No password provided")))
      case _ => Future(None)
    }
  }

  def createAccessToken(authInfo: AuthInfo[User]): Future[AccessToken] = ???

  def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = ???

  def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = ???

  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = ???

  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = ???

  def deleteAuthCode(code: String): Future[Unit] = ???

  def findAccessToken(token: String): Future[Option[AccessToken]] = ???

  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User]]] = ???

}
