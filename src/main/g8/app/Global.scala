import java.io.File

import com.softwaremill.macwire.{MacwireMacros, Macwire}
import com.typesafe.config.ConfigFactory
import play.api.Mode.Mode
import play.api.{Configuration, GlobalSettings}

object Global extends GlobalSettings with Macwire {

  val wired = wiredInModule(new ApplicationModule {})

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    wired.lookupSingleOrThrow(controllerClass)
  }

  override def onLoadConfig(config: Configuration, path: File, classloader: ClassLoader, mode: Mode): Configuration = {
    val modeSpecificConfig = config ++ Configuration(ConfigFactory.load(s"application.${mode.toString.toLowerCase}.conf"))
    super.onLoadConfig(modeSpecificConfig, path, classloader, mode)
  }
}
