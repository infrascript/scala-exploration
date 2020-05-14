package example

sealed class Resource(implicit project: Project, provider: Provider) {
  project.registerResource(this)
  provider.registerResource(this)
}

package object AwsResources {
  import example.types._

  case class AwsAcmCertificate(
      domainName: String,
      subjectAlternateNames: Input[List[String]] = Computed,
      validationMethod: Input[String] = Computed,
      privateKey: Input[String] = Computed,
      certificateBody: Input[String] = Computed,
      certificateChain: Input[String] = Computed,
      certificateAuthorityArn: Input[String] = Computed,
  )(implicit project: Project, provider: Provider)
      extends Resource

}
