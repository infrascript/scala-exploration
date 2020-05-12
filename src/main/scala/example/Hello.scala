package example

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._

object Hello extends Greeting with App {
  import example.types._
  import example.AwsResources._

  implicit val project = new Project
  implicit val provider = new Provider("AWS")

  val cert =
    AwsAcmCertificate(domainName = "example.com", privateKey = "thing")
  println(cert.asJson.dropNullValues)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
