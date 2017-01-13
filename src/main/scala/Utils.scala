import scala.collection.immutable.{Seq, Stream}
import scala.util.Random

/**
  * Created by kuba on 13.01.17
  */
object Utils {

  def generateOrders(n:Int): Seq[Order] = {
    Stream.range(0,n).map(generateOrder)
  }

  def generateOrder(id:Int) : Order = {
    val product = Order.knownProducts(Random.nextInt(Order.knownProducts.length))
    val adress = Order.knownAdresses(Random.nextInt(Order.knownAdresses.length))
    Order(id,product,adress)
  }

  def getMemoryInfo() = {
    "meminfo " + Runtime.getRuntime.totalMemory()/(1024*1024) + "mb / " + Runtime.getRuntime.maxMemory()/(1024*1024) + "mb"
  }
}
