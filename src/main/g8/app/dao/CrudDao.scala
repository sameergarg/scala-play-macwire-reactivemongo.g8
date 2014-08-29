package dao

import scala.concurrent.Future

trait CrudDao[T, R] {

  def findAll(): Future[List[R]]

  def findById(id: T): Future[Option[R]]

  def update(resource: R): Future[Boolean]

  def create(resource: R): Future[R]

  def delete(resource: R): Future[Boolean]

  def deleteById(id: String): Future[Boolean]

}


