package daos

import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoCollection
import com.mongodb. { BasicDBObject, BasicDBList }

import models.DataMapping
import mongo.config.MongoConfig

import models.CategoryData

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[CategoryMappingsDaoImpl])
trait CategoryMappingsDao {
  def set(dataMapping: DataMapping): Unit
  def getGroupedSubCategories(): Array[CategoryData]
}

@Singleton class CategoryMappingsDaoImpl @Inject()(mongo: MongoConfig) extends CategoryMappingsDao {
  val categoryMappings: MongoCollection = mongo.collection("categoryMappings")
  val items: MongoCollection = mongo.collection("items")

  /**
   * Stores a new datamapping in corresponding collection, to know which values(categories and subcategories) should be renamed.
   * @param dataMapping
   */
  override def set(dataMapping: DataMapping): Unit = {
      categoryMappings.findAndModify(
        MongoDBObject("value" -> dataMapping.value),
        MongoDBObject(),
        MongoDBObject(), false, MongoDBObject("$set" -> MongoDBObject("reference" -> dataMapping.reference)), false, true
      )
  }

  /**
   * Fetch subcategories grouped by categories.
   * @return Array[CategoryData]
   */
  override def getGroupedSubCategories(): Array[CategoryData] = {
      items.aggregate(
        List(
          MongoDBObject("$group" ->
            MongoDBObject(
              "_id" -> "$category",
              "subcategories" -> MongoDBObject(
                "$addToSet" -> "$subcategory"
              )
            )
        )
      )
    ).results.map(x => {
      val t = x.asInstanceOf[BasicDBObject]
      val subcategories = t.get("subcategories").asInstanceOf[BasicDBList].toArray
      CategoryData(t.get("_id").toString, subcategories.map(_.toString))
    }).toArray
  }
}
