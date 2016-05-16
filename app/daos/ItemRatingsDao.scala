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

import models.ItemRating

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[ItemRatingsDaoImpl])
trait ItemRatingsDao {
  def add(rating: ItemRating): Unit
}

@Singleton class ItemRatingsDaoImpl @Inject()(mongo: MongoConfig) extends ItemRatingsDao {
  val monthRatings: MongoCollection = mongo.collection("monthly_item_ratings")
  val yearRatings: MongoCollection = mongo.collection("yearly_item_ratings")
  val dayRatings: MongoCollection = mongo.collection("daily_item_ratings")

  def add(filter: ItemRating): Unit = {
      Future {

      }
  }
}
