import ShippingActor.OrdersDone
import akka.actor.{Actor, ActorLogging}

/**
  * Created by kuba on 13.01.17
  */

class ShippingActor extends Actor with ActorLogging {

  override def receive = {
    case order:Order => {
      println(s"generating pdf for order $order")
      Thread.sleep(1000)
      println(s"generated pdf for order $order")
      println(Utils.getMemoryInfo())
    }
    case OrdersDone => {
      println("All shippings generated")
    }
  }
}
object ShippingActor {
  case class PrepareShipping(order: Order)
  case class OrdersDone()
}