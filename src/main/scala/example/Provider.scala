package example

case class Provider(name: String)(implicit ctx: Context) {
  ctx.registerProvider(this)

  def registerResource(resource: Resource): Unit = { println(s"Using provider $name for resource $resource") }
}
