package example
import example.AwsResources._

case class MultipleCertsComponent($uid: String, domain: String)(implicit ctx: Context) {
  // implicit val ctx = this.ctx()

  val rootCert     = AwsAcmCertificate("name", domainName = domain, privateKey = "thing")
  val wildcardCert = AwsAcmCertificate("name", domainName = s"*.$domain", privateKey = "thing")

  // def resources() = {}
}
