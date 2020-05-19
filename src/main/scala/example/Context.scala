package example

import scala.collection.mutable.ArrayBuffer
import scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._

trait InContext {
  def apply[T](fn: => Context => T): T
}

case class Root(ctx: Context) extends Resource("ROOT")(ctx) {
  val children = new ArrayBuffer[Resource]
}

// NOTE: for later, make Context a "resource" on the graph, just as a plain node
case class Context(namespace: Option[String] = None) {
  // data graph
  // ownership tree

  val graph = Graph[Resource, DiEdge]()

  val contextRoot = new Root(this)

  def ns(namespace: String) = {
    val nsCtx = this.copy(namespace = Some(namespace))
    graph.add(nsCtx.contextRoot)
    graph.add(contextRoot ~> nsCtx.contextRoot)
    contextRoot.children.addOne(nsCtx.contextRoot)
    nsCtx
  }

  def withNamespace(namespace: String) = {
    val nsCtx = ns(namespace)
    new InContext {
      def apply[T](inContext: => Context => T) = { inContext(nsCtx) }
    }
  }

  def registerProvider(provider: Provider): Unit = { println(s"Registering provider with context: $provider") }

  def registerResource(resource: Resource): Unit = {
    graph.add(resource)
    resource match {
      // Don't connect root to itself
      case _: Root => ()

      // Connect all other resources to root
      case _ => {
        graph.add(resource)
        graph.add(contextRoot ~> resource)
        contextRoot.children.addOne(resource)
      }
    }
  }
}
