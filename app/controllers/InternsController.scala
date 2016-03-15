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
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import scala.concurrent.Future


/**
  * Created by knoldus on 14/3/16.
  */
class InternsController @Inject()(service:InternRepo) extends Controller{


  val internsForm:Form[Interns] = Form(
    mapping(
      "id" -> number,
      "name" -> text,
      "email" -> text,
      "mobile"-> text,
      "address" -> text,
      "emergency" -> text
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
      anIntern => Ok(views.html.interns((Json.toJson(anIntern)),internsForm))
    }
  }

def insert=Action.async{implicit request=>
  internsForm.bindFromRequest.fold(
    badForm =>Future{ Ok("Error "+badForm)},
    data =>{
      printf("hel0000")
      val id=data.id
      val name=data.name
      val email =data.email
      val mobile=data.mobile
      val address=data.address
      val emergency= data.emergency
      val intern=Interns(id,name,email,mobile,address,emergency)
      printf("hello")
      val res = service.insert(intern)
      res.map{
        r => if(r==1) Redirect("/list") else Ok("Bye")
      }
    })
}

  def editInterns = Action{
 implicit request=>
   Ok
  }


}
