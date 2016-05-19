package services

import com.google.inject.Inject
import com.mongodb.casbah.query.dsl.BSONType.BSONObjectId
import models.User
import org.joda.time.DateTime
import play.api.libs.Crypto

import scala.concurrent.{ExecutionContext, Future}

import scalaoauth2.provider.{AccessToken, AuthInfo, AuthorizationRequest, DataHandler}

class OAuthDataHandler @Inject()(userService: UserService)(implicit  val executionContext: ExecutionContext) extends DataHandler[User] {
  def validateClient(request: AuthorizationRequest): Future[Boolean] = {
    request.clientCredential match {
      case Some(credential) => userService.exists(credential.clientId, credential.clientSecret.getOrElse(throw new Exception("")))
      case _ => Future(false)
    }
  }

  def findUser(request: AuthorizationRequest): Future[Option[User]] = {
    request.clientCredential match {
      case Some(credential) => userService.find(credential.clientId, credential.clientSecret.getOrElse(throw new Exception("")))
      case _ => Future(None)
    }
  }

  def createAccessToken(authInfo: AuthInfo[User]): Future[AccessToken] = {
    val now = new DateTime();
    val refreshToken = Some(Crypto.generateToken(0));
    val accessToken = Crypto.generateToken(0);

    var document = AccessToken(None, accessToken, refreshToken, authInfo.user._id.get,  Some(""), Crypto.accessTokenExpiresIn, new BSONDateTime(now.getTime), authInfo.user.email);
    for{
      _ <- accessTokenService.remove($doc("clientId" $eq authInfo.user.email, "userId" $eq authInfo.user._id.get))
      _ <- accessTokenService.insert(document)
      accessToken <- Future.successful(scalaoauth2.provider.AccessToken(accessToken, refreshToken, authInfo.scope, Some(Crypto.accessTokenExpiresIn.toLong), now))
    }yield accessToken
  }

  def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[AccessToken]] = ???

  def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): Future[AccessToken] = ???

  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[User]]] = ???

  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[User]]] = ???

  def deleteAuthCode(code: String): Future[Unit] = ???

  def findAccessToken(token: String): Future[Option[AccessToken]] = ???

  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[User]]] = ???

}
