package configs

import models.Environments
import play.api.libs.json.Json
import utils.FileUtilsInternal


object EnvironmentConfigs {

  val fileUtilsInternal = new FileUtilsInternal

  def environment():Environments= {

    val jsonEnvironments = fileUtilsInternal.parseFileToJson(System.getProperty("user.dir") + "/src/main/resources/env.json")
    val environmentsValue = Json.fromJson[Environments](jsonEnvironments)
    environmentsValue.getOrElse(Environments).asInstanceOf[Environments]
  }

  final val BLOG_SERVICE_DOMAIN: String = environment().blogService.domain



}

