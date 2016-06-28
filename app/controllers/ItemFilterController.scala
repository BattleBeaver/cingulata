package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.{ItemFilterService, ItemService}

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemFilterController @Inject()(itemService: ItemService, itemFilterService: ItemFilterService) extends Controller {
  def itemsFindByFilterAsJson(page: Int) = Action.async(parse.tolerantJson) {
    implicit request => itemFilterService.findItemsByFilter(request.body, page).map((json: String) => Ok(json).as("application/json"))
  }
}
