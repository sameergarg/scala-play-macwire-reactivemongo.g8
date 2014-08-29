import play.api.libs.json.Json

package object models {

  abstract class Document {
    def _id : String
  }

  object JsonFormats {

    import play.api.libs.json.Json
    import play.api.data._
    import play.api.data.Forms._

    // Generates Writes and Reads for Feed and User thanks to Json Macros
  }

}
