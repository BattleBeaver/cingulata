package services

import com.google.inject.Inject
import models.User1

import scala.concurrent.{ExecutionContext, Future}

import scalaoauth2.provider.{AccessToken, AuthInfo, AuthorizationRequest, DataHandler}

class OAuthDataHandler @Inject()(userService: UserService)(implicit  val executionContext: ExecutionContext) extends DataHandler[User1] {
  def validateClient(request: AuthorizationRequest): Future[Boolean] = {
    request.clientCredential match {
      case Some(credential) => userService.exists(credential.clientId, credential.clientSecret.getOrElse(throw new Exception("No password provided")))
      case _ => Future(false)
    }
  }

  def findUser(request: AuthorizationRequest): Future[Option[User1]] = {
    request.clientCredential match {
      case Some(credential) => userService.find(credential.clientId, credential.clientSecret.getOrElse(throw new Exception("No password provided")))
      case _ => Future(None)
    }
  }

  def createAccessToken(authInfo: AuthInfo[User1]): Future[AccessToken] = ???

  def getStoredAccessToken(authInfo: AuthInfo[User1]): Future[Option[AccessToken]] = ???

  def refreshAccessToken(authInfo: AuthInfo[User1], refreshToken: String): Future[AccessToken] = ???

  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User1]]] = ???

  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User1]]] = ???

  def deleteAuthCode(code: String): Future[Unit] = ???

  def findAccessToken(token: String): Future[Option[AccessToken]] = ???

  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User1]]] = ???

}
