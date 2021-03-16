import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ContentType

import foo.pet._
import foo.definitions.Pet

object App extends App {

  implicit def actorSystem = ActorSystem()

  val routes = PetResource.routes(new PetHandler {
    override def createPet(respond: PetResource.CreatePetResponse.type)(name: Option[String], status: Option[String], file: Option[(java.io.File, Option[String], ContentType)]): scala.concurrent.Future[PetResource.CreatePetResponse] = {
      Future.successful(respond.OK(Pet()))
    }
    override def createPetMapFileField(fieldName: String,fileName: Option[String],contentType: ContentType): java.io.File = ???
    override def updatePet(respond: PetResource.UpdatePetResponse.type)(name: String, body: Option[Pet]): Future[PetResource.UpdatePetResponse] = {
      Future.successful(respond.OK(body.getOrElse(Pet())))
    }
  })

  Await.result(Http().bindAndHandle(routes, "127.0.0.1", 8080), Duration.Inf)
  println("Running at http://localhost:8080 !")
}
