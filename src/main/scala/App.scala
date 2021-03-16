import scala.concurrent._
import nullability.pet._
import nullability.definitions.Pet

object App extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  var actorSystem: Option[(akka.actor.ActorSystem, akka.stream.ActorMaterializer)] = None
  var binding: Option[akka.http.scaladsl.Http.ServerBinding] = None

  def getActorSystem(): (akka.actor.ActorSystem, akka.stream.ActorMaterializer) = {
    actorSystem.getOrElse {
      implicit val localActorSystem = akka.actor.ActorSystem()
      val mat = akka.stream.ActorMaterializer()
      actorSystem = Some((localActorSystem, mat))
      (localActorSystem, mat)
    }
  }

  implicit def localActorSystem: akka.actor.ActorSystem = getActorSystem()._1
  implicit def localMaterializer: akka.stream.ActorMaterializer = getActorSystem()._2

  def unbind(): Unit = {
    import scala.concurrent._
    import scala.concurrent.duration.Duration
    binding.foreach { x =>
      Await.result(x.unbind(), Duration.Inf)
      binding = None
    }
  }

  def bindAndServe(value: akka.http.scaladsl.server.Route): Unit = {
    import scala.concurrent._
    import scala.concurrent.duration._
    unbind()
    binding = Some(Await.result(akka.http.scaladsl.Http().bindAndHandle(value, "127.0.0.1", 8080), Duration.Inf))
    println("Running at http://localhost:8080 !")
  }

  val routes = PetResource.routes(new PetHandler {
    override def createPet(respond: PetResource.CreatePetResponse.type)(name: Option[String], status: Option[String], file: Option[(java.io.File, Option[String], akka.http.scaladsl.model.ContentType)]): scala.concurrent.Future[PetResource.CreatePetResponse] = {
      Future.successful(respond.OK(Pet()))
    }
    override def createPetMapFileField(fieldName: String,fileName: Option[String],contentType: akka.http.scaladsl.model.ContentType): java.io.File = ???
    override def updatePet(respond: PetResource.UpdatePetResponse.type)(name: String, body: Option[Pet]): Future[PetResource.UpdatePetResponse] = {
      Future.successful(Pet())
    }
  })
  bindAndServe(routes)

  Future {
    Thread.sleep(15 * 1000)
    unbind()
    localActorSystem.terminate()
  }
}
