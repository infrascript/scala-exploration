package example

class Resource(implicit ctx: Context) {}

package object AwsResources {
  import example.types._

  case class AwsAcmCertificate(
      $uid: String,
      domainName: String,
      subjectAlternateNames: Input[List[String]] = Computed,
      validationMethod: Input[String] = Computed,
      privateKey: StringRefInput = Computed,
      certificateBody: Input[String] = Computed,
      certificateChain: Input[String] = Computed,
      certificateAuthorityArn: Input[String] = Computed,
  )(implicit ctx: Context)
      extends Resource

}
