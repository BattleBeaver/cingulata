package controllers.admin

import com.google.inject.Inject
import models.{ DataMapping, CategoryData }
import play.api.Play.current
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.{CategoryMappingService, ItemService}
import models.CategoryData
import play.api.libs.json._

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemCategoryController @Inject()(
                                        categoryMappingService: CategoryMappingService,
                                        itemService: ItemService,
                                        val messagesApi: MessagesApi) extends Controller {

  /**
   * Updates category. Inserts new key-value pair in admin.
   * @param oldName: String - old name of category.
   * @param newName: String - new name of category.
   * @return
   */
  def updateCategory(oldName: String, newName: String) = Action.async {
    implicit request => {
      categoryMappingService.set(DataMapping(oldName, newName))
      itemService.setCategoryName(oldName, newName).map((amount: Int) => Ok(amount.toString))
    }
  }

  /**
   * Retrieve grouped subcategories by category in format
   *
   *
   * [
   *    {
   *      "category": "Съемники",
   *      "subcategories": [
   *        "Съемники Jonnesway"
   *      ]
   *    },
   *    {
   *    "category": "Автолампы",
   *      "subcategories": [
   *        "Автолампы SHO-ME",
   *        "Автолампы EX",
   *        "Автолампы IPF",
   *        "Автолампы Solar",
   *        "Автолампы Falcon",
   *        "Автолампы Osram",
   *        "Автолампы Philips",
   *        "Автолампы iDial",
   *        "Автолампы ISKRA",
   *        "Автолампы Mitsumi",
   *        "Автолампы Hella"
   *      ]
   *    }
   * ]
   * @return JSON - F.E. above
   */
  def groupedSubcategories() = Action {
    implicit request => {
      val res = categoryMappingService.getGroupedSubCategories()
      Ok(Json.toJson(res))
    }
  }

   /**
   * Implicit Writes for Category Data representation
   *
   */
  implicit val categoryDataWrites = new Writes[CategoryData] {
    def writes(categoryData: CategoryData) = Json.obj(
      "category" -> categoryData.category,
      "subcategories" -> categoryData.subcategories
    )
  }
}
