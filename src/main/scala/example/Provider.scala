package example

case class Provider(name: String) {
  def registerResource(resource: Resource): Unit = { println(s"Using provider $name for resource $resource") }
}
