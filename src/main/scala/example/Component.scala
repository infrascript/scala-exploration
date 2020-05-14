package example
import example.AwsResources._

trait Component {
  def resources: List[Resource]
}

case class MultipleCertsComponent($uid: String, domain: String)(implicit ctx: Context) extends Component {
  // implicit val ctx = this.ctx()

  val rootCert     = AwsAcmCertificate("name", domainName = domain, privateKey = "thing")
  val wildcardCert = AwsAcmCertificate("name", domainName = s"*.$domain", privateKey = "thing")

  def resources: List[Resource] = List(rootCert, wildcardCert)
}
