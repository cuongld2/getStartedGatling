package configs

import models.General
import play.api.libs.json.Json
import utils.FileUtilsInternal

object GeneralConfigs {

  val fileUtilsInternal = new FileUtilsInternal

  def general():General= {

    val jsonGeneral = fileUtilsInternal.parseFileToJson(System.getProperty("user.dir") + "/src/main/resources/general.json")
    val generalValue = Json.fromJson[General](jsonGeneral)
    generalValue.getOrElse(General).asInstanceOf[General]
  }

  final val BLOG_SERVICE_PATH_USER: String = general().blogService.user
  final val BLOG_SERVICE_PATH_AUTHENTICATE: String = general().blogService.authenticate
  final val BLOG_SERVICE_PATH_BLOG: String = general().blogService.blog

}
