package example

import io.circe.generic.auto._
import io.circe.syntax._

object Hello extends Greeting with App {
  import example.types._
  import example.AwsResources._

  implicit val project  = new Project
  implicit val provider = Provider("AWS")

  val cert = AwsAcmCertificate(domainName = "www.example.com", privateKey = "thing")

  println(cert.asJson.dropNullValues)
  printOption(cert.privateKey)
  printOption(cert.certificateBody)

  val customProvider = Provider("AWS-Custom")

  val multipleCerts = MultipleCertsComponent(domain = "example.com")(provider = customProvider, project = implicitly)
  println(multipleCerts)

  {
    implicit val provider = Provider("CUSTOM")
    Module("module-name")(implicitly) { (_) => AwsAcmCertificate(domainName = "module.example.com") }
  }

  val module = Module("string", Provider("ZZZZ"))
  module { implicit provider: Provider => AwsAcmCertificate(domainName = "imodule.example.com") }
}

trait Greeting {
  lazy val greeting: String = "hello"

  def printOption(v: Option[String]) =
    v match {
      case None        => println("EMPTY")
      case Some(value) => println(value)
    }
}
