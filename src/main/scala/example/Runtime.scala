package example

import example.AwsResources._

object Runtime {
  def render(resources: List[Resource]): Unit = {
    resources.foreach(resource => { println(s"Rendering resource ${resource.computedUID()}: $resource") })
  }

  def tag(input: List[AwsAcmCertificate])(implicit ctx: Context): List[AwsAcmCertificate] = {
    input.map(cert => cert.copy(domainName = "other"))
  }
}
