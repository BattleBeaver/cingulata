package controllers

import com.google.inject.Inject
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.ItemRatingsService

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemRatingsController extends Controller {
  def insert = Action(parse.tolerantJson) {
    implicit request => Ok("Ok")
  }
}
