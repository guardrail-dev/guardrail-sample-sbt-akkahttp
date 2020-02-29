import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.{ Unmarshal, Unmarshaller, FromEntityUnmarshaller, FromRequestUnmarshaller, FromStringUnmarshaller }
import akka.http.scaladsl.marshalling.{ Marshal, Marshaller, Marshalling, ToEntityMarshaller, ToResponseMarshaller }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ Directive, Directive0, Directive1, ExceptionHandler, MalformedHeaderRejection, MissingFormFieldRejection, Rejection, Route }
import akka.http.scaladsl.util.FastFuture
import akka.stream.{ IOResult, Materializer }
import akka.stream.scaladsl.{ FileIO, Keep, Sink, Source }
import akka.util.ByteString
import scala.concurrent._
import foo.pets._

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

  val routes = PetsResource.routes(new PetsHandler {
    override def createPets(respond: PetsResource.createPetsResponse.type)(name: Option[String] = None, status: Option[String] = None, file: Option[(java.io.File, Option[String], akka.http.scaladsl.model.ContentType)]): scala.concurrent.Future[PetsResource.createPetsResponse] = {
      Future.successful(respond.OK((name, status).toString))
    }
    def createPetsMapFileField(fieldName: String,fileName: Option[String],contentType: akka.http.scaladsl.model.ContentType): java.io.File = ???
  })
  bindAndServe(routes)

  Future {
    Thread.sleep(15 * 1000)
    unbind()
    localActorSystem.terminate()
  }
}
