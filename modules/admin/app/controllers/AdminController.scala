package controllers.admin

import com.google.inject.Inject

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.{CategoryMappingService, ItemService, CrawlerService}

import daos.CrawlerDao
/**
  * Created by kuzmentsov@gmail.com
  */
class AdminController @Inject()(crawlerDao: CrawlerDao, categoryMappingService: CategoryMappingService, itemService: ItemService, crawlerService: CrawlerService) extends Controller {

  def index = Action {
    implicit request => {
      Ok(views.html.nav.render())
    }
  }

  /**
   * Returns merged categories template
   * @return merged categories page template.
   */
   def categories = Action.async {
       implicit request => {
         itemService.allCategories.map((categories: Seq[String]) => Ok(views.html.admin.categories(categories)))
       }
   }

   /**
    * Returns merged categories template
    * @return merged categories page template.
    */
    def crawlers = Action {
        implicit request => {
          var list = crawlerDao.findAll()
          Ok(views.html.admin.crawlers.render(list))
        }
    }

   /**
    * Returns merged categories template
    * @return merged categories page template.
    */
    def crawlerDetails = Action {
        implicit request => {
          Ok(views.html.admin.crawlerDetails())
        }
    }

    /**
     * Deletes crawler with a specified id
     * @param crawlerId - Id of crawler
     */
    def modifyCrawler(crawlerId: String) = Action {
      implicit request => {
        val crawler = crawlerService.find(crawlerId)
        println(crawler)
        Ok(views.html.admin.crawlerDetails(crawler))
      }
    }
}
