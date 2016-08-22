package services

import com.google.inject.Inject
import scala.concurrent.Future

import daos.CrawlerDao
import models.Crawler
import play.api.Logger

import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by kuzmentsov@gmail.com
  */
class CrawlerService @Inject()(crawlerDao: CrawlerDao, ws: WSClient) {
  def create(crawler: Crawler) {
    crawlerDao.create(crawler)
  }

  def verifyCrawler(): Future[String] = {
    Logger.info("verifying crawler : ")
    ws.url("http://localhost:9001/crawlers/1/verify").get().map(x => x.body)
  }

  def delete(crawlerId: String): Unit = {
    crawlerDao.delete(crawlerId)
  }
}
