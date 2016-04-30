package daos

import bootstrap.MongoConfig
import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.casbah.{MongoCollection}
import com.mongodb.BasicDBObject
import play.api.libs.json.Json

import com.mongodb.casbah.commons.conversions.scala._
import com.mongodb.casbah.query.Imports._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[ItemFilterDaoImpl]) trait ItemFilterDao {
  def findItemsByFilter(filterJson: play.api.libs.json.JsValue): Future[String]
}

@Singleton class ItemFilterDaoImpl @Inject()(mongo: MongoConfig) extends ItemFilterDao {
  val items: MongoCollection = mongo.collection("items")

  def findItemsByFilter(filterJson: play.api.libs.json.JsValue): Future[String] = {
    val pagingRange = 50

      (filterJson \ "category").asOpt[Array[String]] map (
        categories => return Future { items.find("category" $in categories).toList.map(obj => com.mongodb.util.JSON.serialize(obj)).mkString("[", ",", "]") }
      )
      return Future { "[]" }
  }
}
