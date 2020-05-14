package example

case class Context(namespace: Option[String] = None) {
  def withNamespace(namespace: String) = { this.copy(namespace = Some(namespace)) }

  def registerProvider(provider: Provider): Unit = { println(s"Registering provider with context: $provider") }
}
