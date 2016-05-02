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

import models.filter.InFilterCriteria

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[ItemFilterDaoImpl]) trait ItemFilterDao {
  def findItemsByFilter(filterCriteria: InFilterCriteria): Future[String]
}

@Singleton class ItemFilterDaoImpl @Inject()(mongo: MongoConfig) extends ItemFilterDao {
  val items: MongoCollection = mongo.collection("items")

  def findItemsByFilter(filterCriteria: InFilterCriteria): Future[String] = {
      Future {
        items.find(filterCriteria.what $in filterCriteria.where).toList.map(obj => com.mongodb.util.JSON.serialize(obj)).mkString("[", ",", "]")
      }
  }
}
