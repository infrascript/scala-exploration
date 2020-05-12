package example

class Project {
  def registerResource(resource: Resource): Unit = {
    println("Registering:")
    println(resource)
  }
}
