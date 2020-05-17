package example

import io.circe.generic.auto._
import io.circe.syntax._
import example.AwsResources._

object Hello extends App {
  import example.types._

  val other = Other("name")
  println(other.prop)

  implicit val ctx: Context       = Context()
  implicit val provider: Provider = Provider("AWS")

  val c1: AwsAcmCertificate = AwsAcmCertificate("name", domainName = "thing", privateKey = c2.domainName)
  val c2: AwsAcmCertificate = AwsAcmCertificate("name", domainName = "thing2", privateKey = c1.domainName)

  val component = MultipleCertsComponent("comp", domain = "example.com")

  val certs = ctx.withNamespace("x") { implicit ctx =>
    List(
      AwsAcmCertificate("name", domainName = "module.example.com"),
      AwsAcmCertificate("name", domainName = "module.example.com"),
    )
  }

  val inXYZNamespace = ctx.withNamespace("xyz")
  val otherCerts = List(
    inXYZNamespace { implicit ctx => AwsAcmCertificate("name", domainName = "xyz.example.com") },
    inXYZNamespace { implicit ctx => AwsAcmCertificate("name", domainName = "xyz.example.com") },
  )

  Runtime.render(List.concat(certs, otherCerts, component.resources, List(c1, c2)))
}
