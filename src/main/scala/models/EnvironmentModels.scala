package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads}
import play.api.libs.json._

case class Environments(blogService: BlogService)

case class BlogService(domain: String)

object BlogService{
  implicit val blogServiceReads: Reads[BlogService]=
    (JsPath \ "domain").read[String].map(BlogService.apply)
}

object Environments{
  implicit val environmentReads: Reads[Environments] =
    (JsPath \ "blogService").read[BlogService].map(Environments.apply)
}






