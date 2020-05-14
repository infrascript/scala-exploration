package example

case class Namespace(name: String) {
  def apply(body: => Context => List[Resource])(implicit ctx: Context): List[Resource] = {
    body(ctx.withNamespace(name))
  }

  def apply(body: => Context => Resource)(implicit ctx: Context, d: DummyImplicit): List[Resource] = {
    this.apply(provider => {
      val res = body.apply(provider)
      List(res)
    })
  }
}
