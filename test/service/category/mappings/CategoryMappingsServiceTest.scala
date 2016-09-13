package service.category.mappings

import com.google.inject.Inject
import models.DataMapping
import org.junit.runner._
import org.scalatest.mock.MockitoSugar
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test._
import services.CategoryMappingService

@RunWith(classOf[JUnitRunner])
class CategoryMappingsServiceTest @Inject()(categoryMappingService: CategoryMappingService) extends Specification with MockitoSugar {
  class MockableDataMapping extends DataMapping("from", "to")

  val categoryMapping = mock[MockableDataMapping]

  "CategoryMappingService" should {

    "Create exactly one new mapping first time" in new WithApplication {
      ConsoleLogger.info(categoryMapping.value)
      ConsoleLogger.info(categoryMapping.reference)
      categoryMappingService.set(categoryMapping) must beEqualTo(1)
    }
  }
}
