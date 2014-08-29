package controllers

import play.api.mvc.{Action, Controller}

object HealthCheck extends Controller {

  def ping = Action {
    Ok("pong")
  }


}
