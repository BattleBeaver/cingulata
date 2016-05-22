package controllers.auth

import javax.inject._

import play.api.http.HttpErrorHandler

class Assets @Inject() (errorHandler: HttpErrorHandler) extends controllers.AssetsBuilder(errorHandler)
