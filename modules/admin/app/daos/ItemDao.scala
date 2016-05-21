package daos

import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.BasicDBObject
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoCollection}
import mongo.config.MongoConfig

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.JavaConversions._

import models.Item

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[ItemDaoImpl]) trait ItemDao {
  def allCategories: Future[Seq[String]]
  def setCategoryName(oldName: String, newName: String): Future[Int]
}

@Singleton class ItemDaoImpl @Inject()(mongo: MongoConfig) extends ItemDao {
  val items: MongoCollection = mongo.collection("items")

  def allCategories: Future[Seq[String]] = {
    Future {
      items.distinct("category").map(_.toString)
    }
  }

  override def setCategoryName(oldName: String, newName: String): Future[Int] = {
    Future {
      val bulk = items.initializeOrderedBulkOperation
      bulk.find(MongoDBObject("category" -> oldName)).update(MongoDBObject(
        "$set" -> MongoDBObject("category" -> newName)
      ))
      bulk.execute().getModifiedCount getOrElse -1
    }
  }

  private def objAsItem(obj: BasicDBObject): Item = {
    Item(
      obj.getObjectId("_id").toHexString,
      obj.getString("host"),
      obj.getString("url"),
      obj.getString("title"),
      obj.getString("category"),
      obj.getString("subcategory"),
      obj.getDouble("price"),
      obj.get("features").asInstanceOf[BasicDBObject].map(x => (x._1, x._2.toString)).toMap
    )
  }
}
