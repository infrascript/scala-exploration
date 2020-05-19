package example

import example.AwsResources._
import scalax.collection.Graph
import scalax.collection.io.dot._
import implicits._
import scalax.collection.GraphEdge.DiEdge

object Hello extends App {
  import example.types._

  val other = Other("name")
  println(other.prop)

  implicit val ctx: Context = Context()
  // implicit val provider: Provider = Provider("AWS")

  val c1: AwsAcmCertificate = AwsAcmCertificate("name", domainName = "thing", privateKey = c2.domainName)
  val c2: AwsAcmCertificate = AwsAcmCertificate("name", domainName = "thing2", privateKey = c1.domainName)

  val component = MultipleCertsComponent("comp", domain = "example.com")

  val certs = ctx.withNamespace("x") { implicit ctx =>
    List(
      AwsAcmCertificate("name", domainName = "module.example.com"),
      AwsAcmCertificate("name", domainName = "module.example.com"),
    )
  }

  val inXYZNamespace = ctx.withNamespace("xyz")
  val otherCerts = List(
    inXYZNamespace { implicit ctx => AwsAcmCertificate("name", domainName = "xyz.example.com") },
    inXYZNamespace { implicit ctx => AwsAcmCertificate("name", domainName = "xyz.example.com") },
  )

  Runtime.render(List.concat(certs, otherCerts, List(c1, c2)))

  def printResourceTree(resource: Resource, indent: Int = 0): Unit = {
    println(s"${" " * indent}$resource")

    resource match {
      case root: Root => { root.children.foreach(child => printResourceTree(child, indent + 2)) }
      case _          => ()
    }

  }

  val dotRoot = DotRootGraph(directed = true, id = None)
  def edgeTransformer(innerEdge: Graph[Resource, DiEdge]#EdgeT): Option[(DotGraph, DotEdgeStmt)] =
    innerEdge.edge match {
      case DiEdge(source, target) => {
        println(source)
        Some((dotRoot, DotEdgeStmt(source.toString, target.toString, Nil)))
      }
    }

  println(ctx.graph.toDot(dotRoot, edgeTransformer))
  printResourceTree(ctx.contextRoot)
}
