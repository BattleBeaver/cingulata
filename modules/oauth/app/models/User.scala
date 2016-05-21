package models

import com.mongodb.BasicDBObject
import commons.MongoFormat
import org.bson.types.ObjectId
import play.api.libs.json._

/**
 * @author kuzmentsov@gmail.com
 * @version 1.0
 */

case class User1 (
    val _id: Option[ObjectId],
    val email: String,
    val password: String,
    val registeredOn: Long
)

object UserFormat {
  implicit val userFormat = Json.format[User1]

  implicit val objectIdFormat: Format[ObjectId] = MongoFormat.objectIdFormat
}

object UserConverter {
  def fromBSON(obj: BasicDBObject): User1 = {

    val user1 = User1(
      Some(obj.getObjectId("_id")),
      obj.getString("email"),
      obj.getString("password"),
      obj.getLong("registeredOn")
    )
    println(user1)
    return user1
  }
}
