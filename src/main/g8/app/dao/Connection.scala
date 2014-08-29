package dao

import com.typesafe.config.ConfigFactory
import play.Logger
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.MongoDriver
import scala.concurrent.ExecutionContext.Implicits.global


trait Connection {

  // gets an instance of the driver
  lazy val driver = new MongoDriver
  // (creates an actor system)
  lazy val connectionUri = ConfigFactory.load().getString("mongodb.hostname")+":" + ConfigFactory.load().getString("mongodb.port")
  Logger.info(s"Obtaining connection for uri ${connectionUri}")
  lazy val connection = driver.connection(List(connectionUri))
  // Gets a reference to the database "test"
  lazy val db = connection(ConfigFactory.load().getString("mongodb.db"))


  def getCollection(collectionName: String): JSONCollection = {
    // Gets a reference to the collection "acoll"
    // By default, you get a BSONCollection.
    Logger.info(s"Obtaining collection: ${collectionName} from db: ${db.name}")
    db.collection[JSONCollection](collectionName)
  }


}
