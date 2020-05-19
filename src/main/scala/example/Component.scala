package example
import example.AwsResources._

class Component($uid: String)(implicit ctx: Context) extends Resource($uid) {
  def wrapCtx(): Context = { ctx.ns(s"component-${$uid}") }
}

case class MultipleCertsComponent($uid: String, domain: String)(implicit ctx: Context) extends Component($uid) {
  val c = this.wrapCtx()

  val rootCert     = AwsAcmCertificate("name", domainName = domain, privateKey = "thing")(c)
  val wildcardCert = AwsAcmCertificate("name", domainName = s"*.$domain", privateKey = "thing")(c)

  // def resources() = {}
}
