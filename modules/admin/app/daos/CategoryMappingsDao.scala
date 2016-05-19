package daos

import bootstrap.mongo.MongoConfig
import com.google.inject.{ImplementedBy, Inject, Singleton}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoCollection

import models.DataMapping

/**
  * Created by kuzmentsov@gmail.com
 */
@ImplementedBy(classOf[CategoryMappingsDaoImpl])
trait CategoryMappingsDao {
  def set(dataMapping: DataMapping): Unit
}

@Singleton class CategoryMappingsDaoImpl @Inject()(mongo: MongoConfig) extends CategoryMappingsDao {
  val categoryMappings: MongoCollection = mongo.collection("categoryMappings")

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
}
