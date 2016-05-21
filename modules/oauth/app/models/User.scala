package models

import com.mongodb.BasicDBObject
import commons.MongoFormat
import org.bson.types.ObjectId
import play.api.libs.json._

/**
 * @author kuzmentsov@gmail.com
 * @version 1.0
 */

case class User (
    val _id: Option[ObjectId],
    val email: String,
    val password: String,
    val registeredOn: Long
)

object UserFormat {
  implicit val userFormat = Json.format[User]

  implicit val objectIdFormat: Format[ObjectId] = MongoFormat.objectIdFormat
}

object UserConverter {
  def fromBSON(obj: BasicDBObject): User = {
    User(
      Some(obj.getObjectId("_id")),
      obj.getString("email"),
      obj.getString("password"),
      obj.getLong("registeredOn")
    )
  }
}
