package controllers

import com.google.inject.Inject
import jp.t2v.lab.play2.auth.AuthElement
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import services.{UserService, ItemFilterService, ItemService}

/**
  * Created by kuzmentsov@gmail.com
  */
class TestController @Inject()(implicit val userService: UserService) extends Controller with AuthElement with AuthConfigImpl {
  def test = StackAction(AuthorityKey -> Administrator)  {
    implicit request => Ok("OK")
  }

  sealed trait Role
  case object Administrator extends Role
  case object NormalUser extends Role

  override type Authority = Role
}
