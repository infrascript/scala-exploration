package example

case class Module(name: String, resources: List[Resource] = List(), provider: Provider = null) {
  def apply(body: => Provider => List[Resource])(implicit implicitProvider: Provider): Module = {
    val defaultProvider: Provider = if (provider == null) implicitProvider else provider
    this.copy(resources = body(defaultProvider))
  }

  def apply(body: => Provider => Resource)(implicit implicitProvider: Provider, d: DummyImplicit): Module = {
    this.apply(provider => {
      val res = body.apply(provider)
      List(res)
    })
  }
}
