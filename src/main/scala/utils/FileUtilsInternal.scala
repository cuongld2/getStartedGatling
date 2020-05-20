package utils

import play.api.libs.json.{JsValue, Json}

import scala.io.Source

class FileUtilsInternal {

  def parseFileToJson(pathName: String): JsValue = {

    val bufferSource=Source.fromFile(pathName)
    var jsonFormat: JsValue = null
    try {
      val source:String=bufferSource.getLines.mkString
      jsonFormat = Json.parse(source)
  }
    finally {
      bufferSource.close()
    }
    jsonFormat
  }

}
