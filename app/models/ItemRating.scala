package models

import play.api.libs.json.Json

/**
  * Created by kuzmentsov@gmail.com
  */
case class ItemRating (
                 id: String,
                 itemId: String,
                 date: String,
                 time: Long,
                 title: String,
                 category: String,
                 subcategory: String,
                 owner: String
               )

object ItemRating {
  implicit val itemFormat = Json.format[Item]
}
