package models.filter

/**
 * Filter's abstract type
 */
sealed trait FilterCriteria

/**
 * In clause RESTful representation
 *
 * @param what the name of property to search for
 * @param where criteria to search for
 */
case class In(what: String, where: Array[String]) extends FilterCriteria

/**
 * Full text search RESTful representation
 *
 * @param what the text to search for
 */
case class TextSearch(what: String) extends FilterCriteria

/**
 * Filter representation
 *
 * @param in IN clause representation
 * @see models.filter.In
 * @param textSearch full text search RESTful representation
 * @see models.filter.TextSearch
 */
case class Filter(in: Option[In], textSearch: Option[TextSearch])
