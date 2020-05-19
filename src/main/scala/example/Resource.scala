package example

class Resource($uid: String)(implicit ctx: Context) {
  def kind = getClass().getSimpleName()
  def computedUID() =
    ctx.namespace match {
      case None     => s"${kind}:${$uid}"
      case Some(ns) => s"${ns}:${kind}:${$uid}"
    }

  ctx.registerResource(this)
}

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
      extends Resource($uid) {
    if (privateKey.isInstanceOf[Provided[Any]]) { privateKey.get.setParent(this) }
  }

  case class Other private (prop: String) {}

  object Other {
    def apply(prop: String) = new Other(s"hello $prop")
  }

}
