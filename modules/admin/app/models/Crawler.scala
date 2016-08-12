package models

import play.api.libs.json.Json

/**
  * Created by kuzmentsov@gmail.com
  */
case class Crawler (
  id: Option[String],
  host: String,
  contextRoot: String,
  itemPageExtraParam: String,
  selectors: Selector,
  itemSelector: ItemSelector
)

case class Selector (
  navComponent: String,
  linkToItem: String,
  pagings: String
)

case class ItemSelector (
  title: String,
  price: String,
  category: String,
  subcategory: String,
  imageSrc: String,
  featuresSelector: FeaturesSelector
)

case class FeaturesSelector (
  name: String,
  value: String
)

object Crawler {
  implicit val featuresSelectorFormat = Json.format[FeaturesSelector]
  implicit val itemSelectorFormat = Json.format[ItemSelector]
  implicit val selectorFormat = Json.format[Selector]
  implicit val crawlerFormat = Json.format[Crawler]
}
