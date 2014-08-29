package controllers

import com.typesafe.config.ConfigFactory
import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import scala.concurrent.ExecutionContext.Implicits.global

class Monitor() extends Controller {


  def config = Action {
    Ok(Json.toJson(applicationConfig))
  }

  private def applicationConfig = {
    Map(
      "version" -> ConfigFactory.load().getString("application.version"),
      "mondodb" ->
        s"""${ConfigFactory.load().getString("mongodb.hostname")}:
           |${ConfigFactory.load().getString("mongodb.port")}/
           |${ConfigFactory.load().getString("mongodb.db")}
         """.stripMargin,
      "mongodburi" -> ConfigFactory.load().getString("mongodb.uri")
    )
  }

}