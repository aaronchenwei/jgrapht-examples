package io.github.aaronchenwei.learning.jgrapht

import org.jgrapht.generate.CompleteGraphGenerator
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleGraph
import org.jgrapht.traverse.DepthFirstIterator
import org.jgrapht.util.SupplierUtil
import java.util.function.Supplier
import kotlin.test.BeforeTest
import kotlin.test.Test

class CompleteGraphUnitTest {
  private var completeGraph: SimpleGraph<String, DefaultEdge>? = null
  private var size = 10

  @BeforeTest
  fun createCompleteGraph() {
    // Create the VertexFactory so the generator can create vertices
    val vSupplier: Supplier<String> = object : Supplier<String> {
      private var id = 0
      override fun get(): String {
        return "v" + id++
      }
    }

    // Create the graph object
    completeGraph = SimpleGraph(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false)

    // Create the CompleteGraphGenerator object
    val completeGenerator = CompleteGraphGenerator<String, DefaultEdge>(size)

    // Use the CompleteGraphGenerator object to make completeGraph a
    // complete graph with [size] number of vertices
    completeGenerator.generateGraph(completeGraph)
  }

  @Test
  fun givenCompleteGraph_thenTraversalDFS() {
    // Print out the graph to be sure it's really complete
    val iterator: Iterator<String> = DepthFirstIterator(completeGraph)
    while (iterator.hasNext()) {
      val vertex = iterator.next()
      println(
        "Vertex " + vertex + " is connected to: " + completeGraph!!.edgesOf(vertex).toString()
      )
    }
  }
}
