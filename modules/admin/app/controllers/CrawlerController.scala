package controllers.admin

import com.google.inject.Inject

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.{CategoryMappingService, ItemService}

import models._

import daos.CrawlerDao

/**
  * Created by kuzmentsov@gmail.com
  */
class CrawlerController @Inject()(crawlerDao: CrawlerDao) extends Controller {

  /**
   * Returns merged categories template
   * @return merged categories page template.
   */
  def create = Action {
    implicit request => {
      val crawlerFormData = crawlerForm.bindFromRequest.get
      crawlerDao.create(crawlerFormData)
      Ok("created")
    }
  }

  val crawlerForm = Form(
  mapping(
    "id" -> optional(text),
    "host" -> text,
    "context-root" -> text,
    "itemPageExtraParam" -> text,
    "selector" -> mapping(
      "nav-component" -> text,
      "link-to-item" -> text,
      "pagings" -> text
    )(Selector.apply)(Selector.unapply),
    "itemSelector" -> mapping(
      "title" -> text,
      "price" -> text,
      "category" -> text,
      "subcategory" -> text,
      "imageSrc" -> text,
      "featuresSelector" -> mapping(
        "name" -> text,
        "value" -> text
      )(FeaturesSelector.apply)(FeaturesSelector.unapply)
    )(ItemSelector.apply)(ItemSelector.unapply)
  )(Crawler.apply)(Crawler.unapply)
)
}
