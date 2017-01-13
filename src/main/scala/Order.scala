/**
  * Created by kuba on 13.01.17
  */
case class Order(id:Int,productName:String,adress:String)
object Order {
  def knownProducts = List("Shirt","Shoes","Hat","Trousers")
  def knownAdresses = List("Street 1","Street 2","Street 3","Street 4")
}
