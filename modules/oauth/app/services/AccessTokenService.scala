package services

import java.util.Date

import com.google.inject.Inject
import daos.{AccessTokenDao}
import models.{User, AccessToken}
import monadic.FutureO
import org.joda.time.DateTime
import play.api.libs.Crypto

import scala.concurrent.{ExecutionContext, Future}
import scalaoauth2.provider.{AuthInfo}

/**
  * Created by kuzmentsov@gmail.com
  */
class AccessTokenService @Inject()(accessTokenDao: AccessTokenDao)(implicit ctx: ExecutionContext) {

  def create(authInfo: AuthInfo[User]): Future[scalaoauth2.provider.AccessToken] = {
    println("""


CREATE


      """)
    val now = new Date();
    val refreshToken = Some(Crypto.generateToken);
    val accessToken = Crypto.generateToken;

    val expiresIn = 9999

    val token = AccessToken(
      None,
      accessToken,
      refreshToken,
      authInfo.user._id.getOrElse(throw new RuntimeException("No user _id provided.")),
      Some(""),
      expiresIn,
      new DateTime(),
      authInfo.user.email);

    accessTokenDao.remove(token)
    accessTokenDao.create(token)
    return Future.successful(scalaoauth2.provider.AccessToken(accessToken, refreshToken, authInfo.scope, Some(expiresIn), now))
  }

  def getStoredAccessToken(authInfo: AuthInfo[User]): Future[Option[scalaoauth2.provider.AccessToken]] = {
    val future = accessTokenDao.find(authInfo.user.email, authInfo.user._id.get)
    // future map {
    //   accessToken =>
    //     scalaoauth2.provider.AccessToken(
    //       accessToken.accessToken,
    //       accessToken.refreshToken,
    //       authInfo.scope,
    //       Some(accessToken.expiresIn),
    //       accessToken.createdAt.toDate
    //     )
    // } future
    Future {
      None
    }
  }



  def remove(accessToken: AccessToken): Unit = accessTokenDao.remove(accessToken)
}
