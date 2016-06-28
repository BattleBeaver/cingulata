package services

import com.google.inject.Inject
import daos.{ItemFilterDao}

import scala.concurrent.Future

import models.filter._

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemFilterService @Inject()(itemFilterDao: ItemFilterDao) {
  def findItemsByFilter(filterJson: play.api.libs.json.JsValue, page: Int): Future[String] = {
    val in: Option[In] = (filterJson \ "category").asOpt[Array[String]] match {
      case Some(e) => Some(In("category", e))
      case None => None
    }
    val textSearch: Option[TextSearch] = (filterJson \ "search").asOpt[String] match {
      case Some(e) => Some(TextSearch(e))
      case None => None
    }

    itemFilterDao.findItemsByFilter(Filter(in, textSearch), page)
  }
}
