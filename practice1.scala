import akka.actor._

case class MessageTypeA(says: String)
case class MessageTypeB(says: String)
case class MessageTypeC(says: String)

object MyService extends App {

  var system = ActorSystem("MyService")

  val Router = system.actorOf(Props[Router], "Router")

  Router ! MessageTypeA("Hello type A")
  Router ! MessageTypeB("Hello type B")
  Router ! MessageTypeC("Hello type C")

  system.terminate()
}

class Router extends Actor {

  val typeAProcessor = context.actorOf(Props[TypeAProcessor], "TypeAProcessor")
  val typeBProcessor = context.actorOf(Props[TypeBProcessor], "TypeBProcessor")
  val typeCProcessor = context.actorOf(Props[TypeCProcessor], "TypeCProcessor")

  def receive = {

    case messageTypeA: MessageTypeA =>
      typeAProcessor ! messageTypeA

    case messageTypeB: MessageTypeB =>
      typeBProcessor ! messageTypeB

    case messageTypeC: MessageTypeC =>
      typeCProcessor ! messageTypeC
  }
}

class TypeAProcessor extends Actor {
  def receive = {
    case messageTypeA: MessageTypeA =>
      println(s"MessageTypeA: ${messageTypeA.says}")
  }
}
class TypeBProcessor extends Actor {
  def receive = {
    case messageTypeB: MessageTypeB =>
      println(s"MessageTypeB: ${messageTypeB.says}")
  }
}
class TypeCProcessor extends Actor {
  def receive = {
    case messageTypeC: MessageTypeC =>
      println(s"MessageTypeC: ${messageTypeC.says}")
  }
}

