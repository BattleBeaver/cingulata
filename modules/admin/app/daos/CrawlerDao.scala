package daos

import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.BasicDBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoCollection}
import mongo.config.MongoConfig

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.JavaConversions._

import models.Crawler

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[CrawlerDaoImpl]) trait CrawlerDao {
  def create(crawler: Crawler): Unit
}

@Singleton class CrawlerDaoImpl @Inject()(mongo: MongoConfig) extends CrawlerDao {
  val crawlers: MongoCollection = mongo.collection("crawlers")

  def create(crawler: Crawler): Unit = {
    Future {
      
    }
  }

  // private def objAsItem(obj: BasicDBObject): Item = {
  //   Item(
  //     obj.getObjectId("_id").toHexString,
  //     obj.getString("host"),
  //     obj.getString("url"),
  //     obj.getString("title"),
  //     obj.getString("category"),
  //     obj.getString("subcategory"),
  //     obj.getDouble("price"),
  //     obj.get("features").asInstanceOf[BasicDBObject].map(x => (x._1, x._2.toString)).toMap
  //   )
  // }
}
