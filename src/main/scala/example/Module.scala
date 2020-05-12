package example

case class Module(name: String)(body: () => Unit) {
  implicit val project = new Project
  body()
}
