package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

import play.api.libs.json.{JsPath, Reads}

case class General(blogService: BlogServiceGeneral)

case class BlogServiceGeneral(user: String, authenticate: String, blog: String)

object BlogServiceGeneral{
  implicit val blogServiceGeneralReads: Reads[BlogServiceGeneral]=(
    (JsPath \ "user").read[String] and
      (JsPath \ "authenticate").read[String] and
      (JsPath \ "blog").read[String]
  )(BlogServiceGeneral.apply _)
}

object General{
  implicit val generalReads: Reads[General] =
    (JsPath \ "blogService").read[BlogServiceGeneral].map(General.apply)
}

