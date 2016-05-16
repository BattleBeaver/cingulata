package services

import com.google.inject.Inject
import daos.ItemDao
import models.Item

import scala.concurrent.Future

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemService @Inject()(itemDao: ItemDao) {
  def allCategories: Future[Seq[String]] = itemDao.allCategories

  def setCategoryName(oldName: String, newName: String): Future[Int] =  itemDao.setCategoryName(oldName: String, newName: String)

}
