package models.filter

import com.mongodb.casbah.commons.conversions.scala._

/**
  * Created by kuzmentsov@gmail.com
  */
sealed trait FilterCriteria
case class InFilterCriteria(what: String, where: Array[String]) extends FilterCriteria
