import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ContentType

import foo.pet._
import foo.definitions.Pet
import java.io.File

object App extends App {

  implicit def actorSystem = ActorSystem()

  val routes = PetResource.routes(new PetHandler {
    // application/x-www-form-urlencoded
    def createPet(respond: PetResource.CreatePetResponse.type)(name: String, status: Option[String]): scala.concurrent.Future[PetResource.CreatePetResponse] = {
      Future.successful(respond.OK(Pet(name=name, status=status)))
    }
    // multipart/form-data
    def createPet(respond: PetResource.CreatePetResponse.type)(name: String, status: Option[String], file: Option[(java.io.File, Option[String], ContentType)]): scala.concurrent.Future[PetResource.CreatePetResponse] = {
      Future.successful(respond.OK(Pet(name=name, status=status)))
    }
    def createPetMapFileField(fieldName: String, fileName: Option[String], contentType: ContentType): File = ???

    // application/json
    def createPet(respond: PetResource.CreatePetResponse.type)(body: Pet): Future[PetResource.CreatePetResponse] = {
      Future.successful(respond.OK(body))
    }

    override def updatePet(respond: PetResource.UpdatePetResponse.type)(name: String, body: Option[Pet]): Future[PetResource.UpdatePetResponse] = ???

    def getPets(respond: PetResource.GetPetsResponse.type)(name: Vector[String], status: Option[String]) = ???
  })

  Await.result(Http().newServerAt("127.0.0.1", 8080).bindFlow(routes), Duration.Inf)
  println("Running at http://localhost:8080 !")
}
