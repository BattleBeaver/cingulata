package models.filter

import com.mongodb.casbah.commons.conversions.scala._

/**
  * Created by kuzmentsov@gmail.com
  */
sealed trait FilterCriteria
case class In(what: String, where: Array[String]) extends FilterCriteria
case class TextSearch(what: String) extends FilterCriteria

case class Filter(in: Option[In], textSearch: Option[TextSearch])
