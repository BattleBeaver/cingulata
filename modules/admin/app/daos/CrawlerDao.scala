package daos

import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.{BasicDBObject, DBObject}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoCollection}
import mongo.config.MongoConfig

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.collection.JavaConversions._

import models._

import org.bson.types.ObjectId

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[CrawlerDaoImpl]) trait CrawlerDao {
  def create(crawler: Crawler): Unit
  def delete(crawlerId: String): Unit
  def findAll(): List[Crawler]
}

@Singleton class CrawlerDaoImpl @Inject()(mongo: MongoConfig) extends CrawlerDao {
  val crawlers: MongoCollection = mongo.collection("crawlers")

  def create(crawler: Crawler): Unit = {
    Future {
      crawlers += crawler
    }
  }

  def findAll(): List[Crawler] = {
    crawlers.find().toList.map(x => mongoObject2Crawler(x.asInstanceOf[BasicDBObject]))
  }

  def delete(crawlerId: String): Unit = {
    Future {
      val query = MongoDBObject("_id" -> new ObjectId(crawlerId))
      crawlers.findAndRemove(query)
    }
  }

  /**
  * Implicit conversion from Crawler to MongoDBObject
  *
  */
  private implicit def crawler2MongoObject(crawler: Crawler): DBObject = {
    MongoDBObject(
        "host" -> crawler.host,
        "contextRoot" -> crawler.contextRoot,
        "itemPageExtraParam" -> crawler.itemPageExtraParam,
        "selectors" -> MongoDBObject(
          "navComponent" -> crawler.selectors.navComponent,
          "linkToItem" -> crawler.selectors.linkToItem,
          "pagings" -> crawler.selectors.pagings
        ),
        "itemSelector" -> MongoDBObject(
          "title" -> crawler.itemSelector.title,
          "price" -> crawler.itemSelector.price,
          "category" -> crawler.itemSelector.category,
          "subcategory" -> crawler.itemSelector.subcategory,
          "imageSrc" -> crawler.itemSelector.imageSrc,
          "featuresSelector" -> MongoDBObject(
            "name" -> crawler.itemSelector.featuresSelector.name,
            "value" -> crawler.itemSelector.featuresSelector.value
          )
        )
      )
  }

  /**
  * Implicit conversion from MongoDBObject to Crawler
  *
  */
  private implicit def mongoObject2Crawler(obj: BasicDBObject): Crawler = {
    Crawler(
      Option(obj.getObjectId("_id").toHexString),
      obj.getString("host"),
      obj.getString("contextRoot"),
      obj.getString("itemPageExtraParam"),
      Selector(
        obj.getString("selectors.navComponent"),
        obj.getString("selectors.linkToItem"),
        obj.getString("selectors.pagings")
      ),
      ItemSelector(
        obj.getString("itemSelector.title"),
        obj.getString("itemSelector.price"),
        obj.getString("itemSelector.category"),
        obj.getString("itemSelector.subcategory"),
        obj.getString("itemSelector.imageSrc"),
        FeaturesSelector(
          obj.getString("itemSelector.featuresSelector.name"),
          obj.getString("itemSelector.featuresSelector.value")
        )
      )
    )
  }
}
