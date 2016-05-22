package models

import com.mongodb.{BasicDBObject, DBObject}
import com.mongodb.casbah.commons.MongoDBObject
import commons.MongoFormat
import org.bson.types.ObjectId
import org.joda.time.DateTime
import play.api.libs.json._

/**
 * @author kuzmentsov@gmail.com
 * @version 1.0
 */
case class AccessToken (
    val _id: Option[ObjectId],
    val accessToken: String,
    val refreshToken: Option[String],
    val userId: ObjectId,
    val scope: Option[String],
    val expiresIn: Int,
    val createdAt: DateTime,
    val clientId: String

)

object AccessTokenFormat {
  implicit val objectIdFormat: Format[ObjectId] = MongoFormat.objectIdFormat;

  implicit val accessTokenFormat = Json.format[AccessToken];
}

object AccessTokenConverter {
  def toBSONObject(obj: AccessToken): DBObject = {
    MongoDBObject(
      "accessToken" -> obj.accessToken,
      "refreshToken" -> obj.refreshToken,
      "userId" -> obj.userId,
      "scope" -> obj.scope,
      "expiresIn" -> obj.expiresIn,
      "createdAt" -> obj.createdAt,
      "clientId" -> obj.clientId
    )
  }

  def fromBSONObject(obj: BasicDBObject): AccessToken = {
    AccessToken(
      Some(obj.getObjectId("_id")),
      obj.getString("accessToken"),
      Some(obj.getString("refreshToken")),
      obj.getObjectId("userId"),
      Some(obj.getString("scope")),
      obj.getInt("expiresIn"),
      new DateTime(obj.getDate("createdAt")),
      obj.getString("clientId")
    )
  }
}
