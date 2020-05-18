package example

trait InContext {
  def apply[T](fn: => Context => T): T
}

case class Context(namespace: Option[String] = None) {
  // data graph
  // ownership tree

  def withNamespace(namespace: String) = {
    val nsCtx = this.copy(namespace = Some(namespace))
    new InContext {
      def apply[T](inContext: => Context => T) = { inContext(nsCtx) }
    }
  }

  def registerProvider(provider: Provider): Unit = { println(s"Registering provider with context: $provider") }
}
