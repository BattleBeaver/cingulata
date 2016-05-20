package models

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
  implicit val accessTokenFormat = Json.format[AccessToken];

  implicit val objectIdFormat: Format[ObjectId] = MongoFormat.objectIdFormat;

}
