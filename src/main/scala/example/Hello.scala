package example

import io.circe.generic.auto._
import io.circe.syntax._
import example.AwsResources._

object Hello extends App {
  import example.types._

  implicit val ctx: Context       = Context()
  implicit val provider: Provider = Provider("AWS")

  val c1: AwsAcmCertificate = AwsAcmCertificate("name", domainName = "thing", privateKey = c2.domainName)
  val c2: AwsAcmCertificate = AwsAcmCertificate("name", domainName = "thing2", privateKey = c1.domainName)

  val cert          = AwsAcmCertificate("name", domainName = "www.example.com")
  val multipleCerts = MultipleCertsComponent("comp", domain = "example.com")

  val certs = Namespace("x") { implicit ctx =>
    List(
      AwsAcmCertificate("name", domainName = "module.example.com"),
      AwsAcmCertificate("name", domainName = "module.example.com"),
      AwsAcmCertificate("name", domainName = "module.example.com"),
      AwsAcmCertificate("name", domainName = "module.example.com"),
    )
  }

  Runtime.render(certs ::: cert :: c1 :: c2 :: multipleCerts.resources ::: Nil)
}
