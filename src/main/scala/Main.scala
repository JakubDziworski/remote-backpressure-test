import ShippingActor.OrdersDone
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration._
import scala.util.Success



object OrderingService extends App {

  val config = ConfigFactory.load()
  implicit val actorSystem = ActorSystem("Ordering-service", config.getConfig("ordering-service").withFallback(config))
  val shippingActorSelection = actorSystem.actorSelection("akka.tcp://ShippingActorSystem@127.0.0.1:5153/user/shipping")
  implicit val timeout = Timeout(5 seconds)
  shippingActorSelection.resolveOne().onComplete {
    case Success(actorRef) => sendOrdersToShipping(actorRef)
  }

  def sendOrdersToShipping(shippingActor:ActorRef): Unit = {
    implicit val actorMaterializer = ActorMaterializer()
    val orders = Utils.generateOrders(50000)
    val orderServiceSource = Source(orders)
    val shippingServiceSink = Sink.actorRef(shippingActor,OrdersDone)
    orderServiceSource.toMat(shippingServiceSink)(Keep.left).run
  }
}

object ShippingService {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val actorSystem = ActorSystem("ShippingActorSystem", config.getConfig("shipping-service").withFallback(config))
    actorSystem.actorOf(Props(new ShippingActor), name="shipping")
  }
}