package daos

import com.google.inject.{Inject, ImplementedBy, Singleton}

import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.query.Imports._
import models.{UserConverter, User}
import mongo.config.MongoConfig

import scala.concurrent.{ExecutionContext, Future}
/**
 * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[UserDaoImpl])
trait UserDao {
  def exists(email: String, password: String): Future[Boolean]

  def find(email: String, password: String): Future[Option[User]]
}

@Singleton class UserDaoImpl @Inject()(mongo: MongoConfig)(implicit ctx: ExecutionContext) extends UserDao {
  val users: MongoCollection = mongo.collection("users")

  def exists(email: String, password: String): Future[Boolean] = Future {
    users.findOne($and("email" $eq email, "password" $eq password)).isDefined
  }

  def find(email: String, password: String): Future[Option[User]] = {
    Future {
      Some(
        UserConverter.fromBSON(
          users.findOne($and("email" $eq email, "password" $eq password)).getOrElse(throw new RuntimeException("No users found.")).asInstanceOf[BasicDBObject]
        )
      )
    }
  }
}