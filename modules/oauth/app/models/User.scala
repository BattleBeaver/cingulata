package models

import commons.MongoFormat
import org.bson.types.ObjectId
import play.api.libs.json._

/**
 * @author kuzmentsov@gmail.com
 * @version 1.0
 */
case class User (
    val _id: Option[ObjectId],
    val name: String,
    val email: String,
    val password: String,
    val grantType: String
)

object UserFormat {
  implicit val userFormat = Json.format[User]

  implicit val objectIdFormat: Format[ObjectId] = MongoFormat.objectIdFormat
}
