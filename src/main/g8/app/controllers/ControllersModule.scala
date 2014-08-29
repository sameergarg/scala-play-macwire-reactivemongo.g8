package controllers

import akka.actor.ActorSystem
import com.softwaremill.macwire.MacwireMacros._
import com.typesafe.config.ConfigFactory

trait ControllersModule {

  val actorSystem = ActorSystem("System")

  import com.softwaremill.macwire.MacwireMacros._

  lazy val monitorController = wire[Monitor]


}
