package services

import com.google.inject.Inject
import daos.UserDao
import models.User

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by kuzmentsov@gmail.com
  */
class UserService @Inject()(userDao: UserDao)(implicit ctx: ExecutionContext) {
  def exists(email: String, password: String): Future[Boolean] = userDao.exists(email: String, password: String)

  def find(email: String, password: String): Future[Option[User]] = userDao.find(email: String, password: String)
}
