package example

case class Module(name: String, provider: Provider = null) {
  def apply(body: => Provider => Any)(implicit implicitProvider: Provider): Unit = {
    val defaultProvider: Provider = if (provider == null) implicitProvider else provider
    body(defaultProvider)
  }
}
