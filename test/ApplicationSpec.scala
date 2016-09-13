import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "HTML service" should {

    "Send 200 on /" in new WithApplication {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
    }

    "Send 200 on /items/index" in new WithApplication {
      val items = route(app, FakeRequest(GET, "/items/index")).get

      status(items) must equalTo(OK)
    }

    "Send 200 on /admin" in new WithApplication {
      val admin = route(app, FakeRequest(GET, "/admin")).get

      status(admin) must equalTo(OK)
    }

    "Send 404 on not found page" in new WithApplication {
      val admin = route(app, FakeRequest(GET, "/404status")).get

      status(admin) must equalTo(404)
    }

  }
}
