package blogs

import configs.{EnvironmentConfigs, GeneralConfigs}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class Authenticate extends Simulation{

  val blogServiceProtocol: HttpProtocolBuilder = http.baseUrl(EnvironmentConfigs.BLOG_SERVICE_DOMAIN
    +GeneralConfigs.BLOG_SERVICE_PATH_AUTHENTICATE)

  val scn: ScenarioBuilder = scenario("Check concurrentRequests for authenticate API")
    .exec(
      http("Get game listing api")
        .post("")
        .body(StringBody("""{
                           |	"username": "TestDataClass@gmail.com",
                           |	"password":"Abcd12345#"
                           |}""".stripMargin)).asJson
        .check(status.is(200)))


  val duringSeconds: Integer = Integer.getInteger("duringSeconds",10)
  val constantUsers: Integer = Integer.getInteger("constantUsers",10)

  setUp(scn.inject(constantConcurrentUsers(constantUsers) during (duringSeconds)).protocols(blogServiceProtocol)).maxDuration(1800)
    .assertions(global.responseTime.max.lt(20000), global.successfulRequests.percent.gt(95))

}
