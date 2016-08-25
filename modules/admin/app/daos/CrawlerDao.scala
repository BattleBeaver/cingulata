package daos

import com.google.inject.{ ImplementedBy, Inject, Singleton }
import com.mongodb.{ BasicDBObject, DBObject }
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{ MongoCollection }
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
  def find(crawlerId: String): Option[Crawler]
  def findAll(): List[Crawler]
}

@Singleton class CrawlerDaoImpl @Inject()(mongo: MongoConfig) extends CrawlerDao {
  val crawlers: MongoCollection = mongo.collection("crawlers")

  def create(crawler: Crawler): Unit = {
    Future {
      crawlers += crawler
    }
  }

  def find(crawlerId: String): Option[Crawler] = {
    val query = MongoDBObject("_id" -> new ObjectId(crawlerId))
    crawlers.findOne(query) match {
      case Some(e) => Option(e.asInstanceOf[BasicDBObject])
      case _ => None
    }
  }

  /**
  * Find all available crawlers
  * @return List of Crawler
  */
  def findAll(): List[Crawler] = {
    crawlers.find().toList.map(x => mongoObject2Crawler(x.asInstanceOf[BasicDBObject]))
  }

  /**
  * Delete Crawler by Id
  * @param crawlerId - Id of crawler to be deleted
  *
  */
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
  * @param obj: BasicDBObject - object to convert
  * @return Crawler
  */
  private implicit def mongoObject2Crawler(obj: BasicDBObject): Crawler = {
    val selectors = obj.get("selectors").asInstanceOf[BasicDBObject]
    val itemSelectors = obj.get("itemSelector").asInstanceOf[BasicDBObject]
    val featuresSelector = itemSelectors.get("featuresSelector").asInstanceOf[BasicDBObject]
    Crawler(
      Option(obj.getObjectId("_id").toHexString),
      obj.getString("host"),
      obj.getString("contextRoot"),
      obj.getString("itemPageExtraParam"),
      Selector(
        selectors.getString("navComponent"),
        selectors.getString("linkToItem"),
        selectors.getString("pagings")
      ),
      ItemSelector(
        itemSelectors.getString("title"),
        itemSelectors.getString("price"),
        itemSelectors.getString("category"),
        itemSelectors.getString("subcategory"),
        itemSelectors.getString("imageSrc"),
        FeaturesSelector(
          featuresSelector.getString("name"),
          featuresSelector.getString("value")
        )
      )
    )
  }
}
