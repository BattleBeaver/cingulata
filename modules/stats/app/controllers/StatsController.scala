package controllers.stats

import play.api.Play.current
import play.api.mvc._

class StatsController extends Controller {
  def index = Action {
      implicit request => Ok("StatsController get")
  }
}
