package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.{ItemFilterService, ItemService}

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemFilterController @Inject()(itemService: ItemService, itemFilterService: ItemFilterService) extends Controller {
  def itemsFindByFilterAsJson = Action.async(parse.tolerantJson) {
    implicit request => itemFilterService.findItemsByFilter(request.body).map((json: String) => Ok(json).as("application/json"))
  }

  def itemsCountByFilterAsJson = Action.async(parse.tolerantJson) {
    implicit request => itemFilterService.countItemsByFilter(request.body).map((json: Int) => Ok(json toString).as("application/json"))
  }
}
