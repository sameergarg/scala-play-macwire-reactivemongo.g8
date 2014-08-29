package dao

import akka.actor.Status.{Failure, Success}
import com.github.simplyscala.MongoEmbedDatabase
import models.{Document}
import org.scalatest.mock.MockitoSugar
import org.scalatest.{OneInstancePerTest, BeforeAndAfter}
import org.scalatestplus.play.OneAppPerSuite
import play.Logger
import play.api.GlobalSettings
import play.api.{Application}
import play.api.libs.json.{Format, Json}
import play.api.test.FakeApplication

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import org.scalatest.Matchers._


class BooksDaoTest extends org.scalatest.FunSuite with MongoEmbedDatabase with OneAppPerSuite with MockitoSugar {
  // Override app if you need a FakeApplication with other than
  // default parameters.
  /*implicit override lazy val app: FakeApplication =
    FakeApplication(
      //additionalConfiguration = Map("mongodb.port" -> 12345),
      withGlobal = Some(new GlobalSettings {
        override def onStart(app: Application) {
          println("Hello world!")
        }
      }))*/

  test("create a book") {
    withEmbedMongoFixture() { mongodProps =>
      val booksDao = new BooksDao()
      val book = booksDao.create(Book("1", "Harry Potter"))

      assert(Await.result(book, 10 seconds) != null)
    }
  }

  test("find all books") {
    withEmbedMongoFixture() {
      mongodProps =>
        val booksDao = new BooksDao()
        val books: Future[List[Book]] = booksDao.create(Book("2", "Harry Potter")).flatMap { book =>
          booksDao.findAll()
        }

        assert(Await.result(books, 10 seconds).isEmpty == false)

    }
  }

  test("delete a book") {
    withEmbedMongoFixture() {
      mondodProps =>
        val booksDao = new BooksDao()

        val harryPotter = Book("3", "Harry Potter")
        Await.result(booksDao.create(harryPotter), 3 seconds)
        val allBooks = Await.result(booksDao.findAll(), 10 second)
        val deleted = Await.result(booksDao.delete(allBooks.head), 10 second)
        Logger.info(s"book deleted ${deleted}")
        deleted should be(true)
      //deletedBooks should not be empty
    }
  }

  class BooksDao extends MongoDocumentsDao[String, Book] {

    override implicit val formatter: Format[Book] = Book.bookFormatter

    override val collectionName: String = "books"
  }

  case class Book(_id: String, name: String) extends Document

  object Book {
    implicit val bookFormatter: Format[Book] = Json.format[Book]
  }


}
