package example

import io.circe.generic.auto._
import io.circe.syntax._

object Runtime {
  def render(resources: List[Resource]): Unit = { resources.foreach(resource => { println(s"Rendering: $resource") }) }
}

object Hello extends Greeting with App {
  import example.AwsResources._
  import example.types._

  implicit val project: Project   = new Project
  implicit val provider: Provider = Provider("AWS")

  val cert = AwsAcmCertificate(domainName = "www.example.com", privateKey = "thing")

  println(cert.asJson.dropNullValues)
  printOption(cert.privateKey)
  printOption(cert.certificateBody)

  val customProvider = Provider("AWS-Custom")

  val multipleCerts = MultipleCertsComponent(domain = "example.com")(provider = customProvider, project = implicitly)
  println(multipleCerts)

  {
    implicit val provider: Provider = Provider("CUSTOM")
    Module("module-name") { _ => AwsAcmCertificate(domainName = "module.example.com") }
  }

  val stringMod = Module("string", provider = Provider("ZZZZ")) { implicit provider: Provider =>
    AwsAcmCertificate(domainName = "i-module.example.com")
  }

  Runtime.render(stringMod.resources)
}

trait Greeting {
  lazy val greeting: String = "hello"

  def printOption(v: Option[String]): Unit =
    v match {
      case None        => println("EMPTY")
      case Some(value) => println(value)
    }
}
