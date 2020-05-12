package example

case class Provider(name: String) {
  def registerResource(resource: Resource) {
    println(s"Using provider $name")
  }
}
