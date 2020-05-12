package example

class Resource(implicit project: Project, provider: Provider) {
  project.registerResource(this)
  provider.registerResource(this)
}

package object AwsResources {
  import example.types._

  case class AwsAcmCertificate(
      domainName: String,
      subjectAlternateNames: Input[List[String]] = None,
      validationMethod: Input[String] = None,
      privateKey: Input[String] = None,
      certificateBody: Input[String] = None,
      certificateChain: Input[String] = None,
      certificateAuthorityArn: Input[String] = None
  )(implicit project: Project, provider: Provider)
      extends Resource

}
