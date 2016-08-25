package controllers.admin

import com.google.inject.Inject

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import services.CrawlerService

import models._
import akka.actor._

/**
  * Created by kuzmentsov@gmail.com
  */
class CrawlerController @Inject()(crawlerService: CrawlerService) extends Controller {

  /**
   * Returns merged categories template
   * @return merged categories page template.
   */
  def create = Action {
    implicit request => {
      val crawlerFormData = crawlerForm.bindFromRequest.get
      println(crawlerFormData)
      crawlerService.create(crawlerFormData)
      Ok("Created")
    }
  }

  /**
   * Deletes crawler with a specified id
   */
  def delete(crawlerId: String) = Action {
    implicit request => {
      crawlerService.delete(crawlerId)
      Ok("Deleted")
    }
  }

  /**
  * Verifies Crawler's settings if they are properly configured.
  * @return Future of message.
  */
  def verifyCrawler = Action.async {
    val result = crawlerService.verifyCrawler();
    result map {
      x => Ok(x)
    }
  }

  val crawlerForm = Form(
    mapping(
      "id" -> optional(text),
      "host" -> text,
      "contextRoot" -> text,
      "itemPageExtraParam" -> text,
      "selector" -> mapping(
        "navComponent" -> text,
        "linkToItem" -> text,
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
