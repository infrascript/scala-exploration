package example

case class Module(name: String, provider: Provider = null)(implicit implicitProvider: Provider) {
  val defaultProvider: Provider = if (provider == null) implicitProvider else provider

  def apply(body: => Provider => Any): Unit = { body(defaultProvider) }
}
