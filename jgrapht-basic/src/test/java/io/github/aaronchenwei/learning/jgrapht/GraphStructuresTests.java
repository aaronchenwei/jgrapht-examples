package io.github.aaronchenwei.learning.jgrapht;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(OrderAnnotation.class)
public class GraphStructuresTests {

  private static final Logger LOGGER = LoggerFactory.getLogger(GraphStructuresTests.class);

  private static final List<String> VERTEX_LIST = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");

  @Test
  @DisplayName("SimpleGraph")
  @Order(1)
  void testSimpleGraph() {
    var graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge("b", "c");
    /*
    some graphs do not allow edge-multiplicity.
    In such cases, if the graph already contains an edge from the specified source to the specified target,
    then this method does not change the graph and returns null.
     */
    var edge2 = graph.addEdge("b", "c");
    var edge3 = graph.addEdge("c", "b");

    graph.addEdge("c", "d");
    graph.addEdge("c", "e");
    graph.addEdge("e", "f");
    graph.addEdge("e", "g");
    graph.addEdge("e", "h");
    graph.addEdge("f", "g");
    graph.addEdge("f", "h");
    graph.addEdge("g", "h");

    assertNotNull(edge1);
    assertNull(edge2);
    assertNull(edge3);
    assertEquals(8, graph.vertexSet().size());
    assertEquals(9, graph.edgeSet().size());

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("Multigraph")
  @Order(2)
  void testMultigraph() {
    var graph = new Multigraph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge("b", "c");
    var edge2 = graph.addEdge("b", "c");
    var edge3 = graph.addEdge("c", "b");

    assertNotEquals(edge1, edge2);
    assertNotEquals(edge1, edge3);
    assertEquals(8, graph.vertexSet().size());
    assertEquals(3, graph.edgeSet().size());

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("Pseudograph")
  @Order(3)
  void testPseudograph() {
    var graph = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge("b", "c");
    var edge2 = graph.addEdge("b", "c");
    var edge3 = graph.addEdge("c", "b");
    var edge4 = graph.addEdge("a", "a");

    assertNotEquals(edge1, edge2);
    assertNotEquals(edge1, edge3);
    assertNotNull(edge4);
    assertEquals(8, graph.vertexSet().size());
    assertEquals(4, graph.edgeSet().size());

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultUndirectedGraph")
  @Order(4)
  void testDefaultUndirectedGraph() {
    var graph = new DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge("b", "c");
    var edge2 = graph.addEdge("b", "c");
    var edge3 = graph.addEdge("c", "b");
    var edge4 = graph.addEdge("a", "a");

    assertNotNull(edge1);
    assertNull(edge2);
    assertNull(edge3);
    assertNotNull(edge4);
    assertEquals(8, graph.vertexSet().size());
    assertEquals(2, graph.edgeSet().size());

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SimpleWeightedGraph")
  @Order(5)
  void testSimpleWeightedGraph() {
    var graph = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge("b", "c");
    var edge2 = graph.addEdge("b", "c");
    var edge3 = graph.addEdge("c", "b");
    graph.setEdgeWeight(edge1, 1.0);

    assertThrows(IllegalArgumentException.class, () -> graph.addEdge("a", "a"));

    assertEquals(1.0, graph.getEdgeWeight(edge1));
    assertNull(edge2);
    assertNull(edge3);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("WeightedMultigraph")
  @Order(6)
  void testWeightedMultigraph() {
    var graph = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge("a", "b");
    var edge2 = graph.addEdge("a", "b");
    var edge3 = graph.addEdge("b", "c");
    var edge4 = graph.addEdge("c", "b");
    graph.setEdgeWeight(edge1, 1.0);
    graph.setEdgeWeight(edge2, 2.0);
    graph.setEdgeWeight(edge3, 4.0);
    graph.setEdgeWeight(edge4, 8.0);

    assertEquals(2, graph.getAllEdges("a", "b").size());
    assertEquals(2, graph.getAllEdges("b", "c").size());

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("WeightedPseudograph")
  @Order(7)
  void testWeightedPseudograph() {
    var graph = new WeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultUndirectedWeightedGraph")
  @Order(8)
  void testDefaultUndirectedWeightedGraph() {
    var graph = new DefaultUndirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SimpleDirectedGraph")
  @Order(9)
  void testSimpleDirectedGraph() {
    var graph = new SimpleDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedMultigraph")
  @Order(10)
  void testDirectedMultigraph() {
    var graph = new DirectedMultigraph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedPseudograph")
  @Order(11)
  void testDirectedPseudograph() {
    var graph = new DirectedPseudograph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultDirectedGraph")
  @Order(12)
  void testDefaultDirectedGraph() {
    var graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SimpleDirectedWeightedGraph")
  @Order(13)
  void testSimpleDirectedWeightedGraph() {
    var graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedWeightedMultigraph")
  @Order(14)
  void testDirectedWeightedMultigraph() {
    var graph = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedWeightedPseudograph")
  @Order(15)
  void testDirectedWeightedPseudograph() {
    var graph = new DirectedWeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultDirectedWeightedGraph")
  @Order(16)
  void testDefaultDirectedWeightedGraph() {
    var graph = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }
}
