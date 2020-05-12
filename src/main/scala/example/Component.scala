package example

case class MultipleCertsComponent(domain: String)(implicit provider: Provider, project: Project) {
  import example.AwsResources._

  val rootCert     = AwsAcmCertificate(domainName = domain, privateKey = "thing")
  val wildcardCert = AwsAcmCertificate(domainName = s"*.$domain", privateKey = "thing")
}
