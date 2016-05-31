package controllers.auth

import com.google.inject.Inject

import play.api.http.HttpErrorHandler

class Assets @Inject() (errorHandler: HttpErrorHandler) extends controllers.AssetsBuilder(errorHandler)
