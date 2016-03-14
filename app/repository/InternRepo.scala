package repository


import com.google.inject.{Inject}
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.Future


case class Interns(id:Int,name:String,email:String,mobile:String,address:String,emergency:String)

trait InternTable{ self: HasDatabaseConfigProvider[JdbcProfile] =>
  import driver.api._

  class InternTable(tag:Tag) extends Table[Interns](tag,"interns") {
    val id=column[Int]("id",O.AutoInc,O.PrimaryKey)
    val name= column[String]("name", O.SqlType("VARCHAR(100)"))
    val email= column[String]("email", O.SqlType("VARCHAR(100)"))
    val mobile= column[String]("mobile", O.SqlType("VARCHAR(10)"))
    val address= column[String]("address", O.SqlType("VARCHAR(100)"))
    val emergency= column[String]("emergency", O.SqlType("VARCHAR(10)"))

    def * = (id, name,email,mobile,address,emergency) <> (Interns.tupled,Interns.unapply)
  }

  val internTableQuery = TableQuery[InternTable]
}

class InternRepo @Inject()(protected val dbConfigProvider:DatabaseConfigProvider) extends InternTable with HasDatabaseConfigProvider[JdbcProfile]{
  import driver.api._

  def insert(intern:Interns):Future[Int] = {
    db.run {
      internTableQuery += intern
    }
  }

  def update(intern:Interns):Future[Int]={
    db.run {internTableQuery.filter(_.id === intern.id).update(intern)}
  }

  def delete(id: Int):Future[Int]={
    db.run {internTableQuery.filter(_.id === id).delete}
  }

  def getAll():Future[List[Interns]]={
    db.run{ internTableQuery.to[List].result}
  }

  def getById(id:Int):Future[Option[Interns]]={
    db.run{
      internTableQuery.filter(_.id===id).result.headOption
    }
  }

}