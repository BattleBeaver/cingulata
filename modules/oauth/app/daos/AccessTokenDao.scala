package daos

import com.google.inject.{Inject, ImplementedBy, Singleton}

import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.query.Imports._
import models.{AccessTokenConverter, AccessToken}
import mongo.config.MongoConfig

import scala.concurrent.{ExecutionContext, Future}

/**
 * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[AccessTokenDaoImpl])
trait AccessTokenDao {
  def create(accessToken: AccessToken): Unit

  def remove(accessToken: AccessToken): Unit

  def find(clientId: String, userId: ObjectId): Future[Option[AccessToken]]
}

@Singleton class AccessTokenDaoImpl @Inject()(mongo: MongoConfig)(implicit ctx: ExecutionContext) extends AccessTokenDao {
  val tokens: MongoCollection = mongo.collection("stored_tokens")

  def create(accessToken: AccessToken): Unit = Future {
    tokens += AccessTokenConverter.toBSONObject(accessToken)
  }

  def remove(accessToken: AccessToken): Unit = Future {
    tokens.findAndRemove($and("clientId" $eq accessToken.clientId, "userId" $eq accessToken.userId))
  }

  def find(clientId: String, userId: ObjectId): Future[Option[AccessToken]] = Future {
    tokens.findOne($and("clientId" $eq clientId, "userId" $eq userId)) match {
      case Some(x) => return Future.successful(Some(AccessTokenConverter.fromBSONObject(x.asInstanceOf[BasicDBObject])))
      case None => return Future.successful(None)
    }
  }
}
