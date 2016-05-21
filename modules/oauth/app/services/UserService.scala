package services

import com.google.inject.Inject
import daos.UserDao
import models.User1

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by kuzmentsov@gmail.com
  */
class UserService @Inject()(userDao: UserDao) {
  def exists(email: String, password: String): Future[Boolean] = userDao.exists(email: String, password: String)

  def find(email: String, password: String): Future[Option[User1]] = userDao.find(email: String, password: String)
}
