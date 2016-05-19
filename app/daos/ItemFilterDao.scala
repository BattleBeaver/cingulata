package daos

import bootstrap.mongo.MongoConfig
import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.casbah.{MongoCollection}

import com.mongodb.casbah.query.Imports._

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

import models.filter.Filter

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[ItemFilterDaoImpl])
trait ItemFilterDao {
  def findItemsByFilter(filter: Filter): Future[String]
}

@Singleton class ItemFilterDaoImpl @Inject()(mongo: MongoConfig) extends ItemFilterDao {
  val items: MongoCollection = mongo.collection("items")

  def findItemsByFilter(filter: Filter): Future[String] = {
      val limit = 50;
      Future {
        var query: com.mongodb.DBObject = null
        if (filter.in.isDefined && filter.textSearch.isDefined) {
          query = $and(filter.in.get.what $in filter.in.get.where, "title" $eq s"(?i).*${filter.textSearch.get.what}*".r)
        } else if (filter.in.isDefined) {
          query = filter.in.get.what $in filter.in.get.where
        } else if (filter.textSearch.isDefined) {
          query = "title" $eq s"(?i).*${filter.textSearch.get.what}.*".r
        }
        items.find(query).limit(limit).toList.map(obj => com.mongodb.util.JSON.serialize(obj)).mkString("[", ",", "]")
      }
  }
}
