package controllers

import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{Json, JsPath, Writes}
import play.api.mvc._
import repository.InternRepo
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import repository.Interns
/**
  * Created by knoldus on 14/3/16.
  */
class InternsController @Inject()(service:InternRepo) extends Controller{


  val internsForm:Form[Interns] = Form(
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "mobile"-> nonEmptyText,
      "address" -> nonEmptyText,
      "emergency" -> nonEmptyText
    )(Interns.apply)(Interns.unapply)
  )

  implicit val internReads: Writes[Interns] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "name").write[String] and
      (JsPath \"email").write[String] and
      (JsPath \ "mobile").write[String] and
      (JsPath \ "address").write[String] and
      (JsPath \ "emergency").write[String] )(unlift(Interns.unapply))

  def list = Action.async { implicit request =>
    val list = service.getAll()
    list.map {
      anIntern => Ok(views.html.interns((Json.toJson(anIntern))))
    }
  }

  def editInterns = Action{
 implicit request=>
   Ok
  }


}
