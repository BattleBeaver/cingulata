package services

import com.google.inject.Inject
import daos.ItemRatingsDao

import scala.concurrent.Future

import models.ItemRating

/**
  * Created by kuzmentsov@gmail.com
  */
class ItemRatingsService @Inject()(itemRatingsDao: ItemRatingsDao) {
  def add(itemRating: ItemRating): Unit = itemRatingsDao.add(itemRating)
}
