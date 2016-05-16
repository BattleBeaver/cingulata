package controllers

import com.google.inject.Inject
import controllers.AuthConfigImpl
import jp.t2v.lab.play2.auth.LoginLogout
import play.api.mvc.{Action, Controller}
import play.api.data.Form
import play.api.data.Forms._
import services.UserService
import views.html

import scala.concurrent.ExecutionContext.Implicits.global

class AuthenticationController @Inject()(implicit val userService: UserService) extends Controller with LoginLogout with AuthConfigImpl {

  // Sign in
  case class LoginData(username:String, password:String)
  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginData.apply)(LoginData.unapply))

  /** Alter the login page action to suit your application. */
  def signin = Action { implicit request =>
    Ok("/")
  }

  def logout = Action.async { implicit request =>
    // do something...
    gotoLogoutSucceeded
  }


  override type Authority = this.type
}
