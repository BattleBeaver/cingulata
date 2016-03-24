package bootstrap

import com.google.inject.{Inject, Singleton}
import com.mongodb.ServerAddress
import com.mongodb.casbah._
import play.Logger
import play.api.Configuration
import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

/**
  * Created by kuzmentsov@gmail.com
  */
@Singleton
class MongoConfig @Inject()(applicationLifecycle: ApplicationLifecycle, configuration: Configuration) {
  //init of connection
  private lazy val server = new ServerAddress(configuration.getString("mongo.db.host").get, configuration.getInt("mongo.db.port").get)

  //credentials to access database
  private lazy val credentials = MongoCredential.createCredential(
    configuration.getString("mongo.db.username").get,
    configuration.getString("mongo.db.name").get,
    configuration.getString("mongo.db.password").get.toCharArray
  )

  private val client = MongoClient(List(server), List(credentials))

  //selecting collection from properties
  val collection: MongoDB = client(configuration.getString("mongo.db.name").get)

  applicationLifecycle.addStopHook(() => {
    Logger.warn("Closing Mongo connection")
    Future.successful(client.close())
  })
}
