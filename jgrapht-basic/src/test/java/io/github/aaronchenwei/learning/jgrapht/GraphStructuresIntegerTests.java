package io.github.aaronchenwei.learning.jgrapht;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.alg.util.Triple;
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
import org.jgrapht.opt.graph.sparse.SparseIntUndirectedGraph;
import org.jgrapht.opt.graph.sparse.SparseIntUndirectedWeightedGraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(OrderAnnotation.class)
public class GraphStructuresIntegerTests {

  private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final List<Integer> VERTEX_LIST = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

  @Test
  @DisplayName("SimpleGraph")
  @Order(1)
  void testSimpleGraph() {
    var graph = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge(1, 2);
    /*
    some graphs do not allow edge-multiplicity.
    In such cases, if the graph already contains an edge from the specified source to the specified target,
    then this method does not change the graph and returns null.
     */
    var edge2 = graph.addEdge(1, 2);
    var edge3 = graph.addEdge(2, 1);

    graph.addEdge(2, 3);
    graph.addEdge(3, 4);
    graph.addEdge(4, 5);
    graph.addEdge(4, 6);
    graph.addEdge(4, 7);
    graph.addEdge(5, 7);
    graph.addEdge(6, 8);
    graph.addEdge(7, 8);

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
    var graph = new Multigraph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge(1, 2);
    var edge2 = graph.addEdge(1, 2);
    var edge3 = graph.addEdge(2, 3);

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
    var graph = new Pseudograph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge(1, 2);
    var edge2 = graph.addEdge(1, 2);
    var edge3 = graph.addEdge(2, 1);
    var edge4 = graph.addEdge(1, 1);

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
    var graph = new DefaultUndirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge(1, 2);
    var edge2 = graph.addEdge(1, 2);
    var edge3 = graph.addEdge(2, 1);
    var edge4 = graph.addEdge(1, 1);

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
    var graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge(1, 2);
    var edge2 = graph.addEdge(1, 2);
    var edge3 = graph.addEdge(2, 1);
    graph.setEdgeWeight(edge1, 1.0);

    assertThrows(IllegalArgumentException.class, () -> graph.addEdge(1, 1));

    assertEquals(1.0, graph.getEdgeWeight(edge1));
    assertNull(edge2);
    assertNull(edge3);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("WeightedMultigraph")
  @Order(6)
  void testWeightedMultigraph() {
    var graph = new WeightedMultigraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);
    var edge1 = graph.addEdge(1, 2);
    var edge2 = graph.addEdge(1, 2);
    var edge3 = graph.addEdge(2, 3);
    var edge4 = graph.addEdge(3, 2);
    graph.setEdgeWeight(edge1, 1.0);
    graph.setEdgeWeight(edge2, 2.0);
    graph.setEdgeWeight(edge3, 4.0);
    graph.setEdgeWeight(edge4, 8.0);

    assertEquals(2, graph.getAllEdges(1, 2).size());
    assertEquals(2, graph.getAllEdges(2, 3).size());

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("WeightedPseudograph")
  @Order(7)
  void testWeightedPseudograph() {
    var graph = new WeightedPseudograph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultUndirectedWeightedGraph")
  @Order(8)
  void testDefaultUndirectedWeightedGraph() {
    var graph = new DefaultUndirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SimpleDirectedGraph")
  @Order(9)
  void testSimpleDirectedGraph() {
    var graph = new SimpleDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedMultigraph")
  @Order(10)
  void testDirectedMultigraph() {
    var graph = new DirectedMultigraph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedPseudograph")
  @Order(11)
  void testDirectedPseudograph() {
    var graph = new DirectedPseudograph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultDirectedGraph")
  @Order(12)
  void testDefaultDirectedGraph() {
    var graph = new DefaultDirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SimpleDirectedWeightedGraph")
  @Order(13)
  void testSimpleDirectedWeightedGraph() {
    var graph = new SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedWeightedMultigraph")
  @Order(14)
  void testDirectedWeightedMultigraph() {
    var graph = new DirectedWeightedMultigraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DirectedWeightedPseudograph")
  @Order(15)
  void testDirectedWeightedPseudograph() {
    var graph = new DirectedWeightedPseudograph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("DefaultDirectedWeightedGraph")
  @Order(16)
  void testDefaultDirectedWeightedGraph() {
    var graph = new DefaultDirectedWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    Graphs.addAllVertices(graph, VERTEX_LIST);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SparseIntUndirectedGraph")
  @Order(17)
  void testSparseIntUndirectedGraph() {
    final Integer vertexCount = 6;
    List<Pair<Integer, Integer>> edges = Arrays.asList(
      Pair.of(0, 5),
      Pair.of(0, 2),
      Pair.of(3, 4),
      Pair.of(1, 4),
      Pair.of(0, 1),
      Pair.of(3, 1),
      Pair.of(2, 4));
    var graph = new SparseIntUndirectedGraph(vertexCount, edges);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SparseIntUndirectedWeightedGraph")
  @Order(18)
  void testSparseIntUndirectedWeightedGraph() {
    final Integer vertexCount = 6;
    List<Triple<Integer, Integer, Double>> edges = Arrays.asList(
      Triple.of(0, 5, 1d),
      Triple.of(0, 2, 2d),
      Triple.of(3, 4, 3d),
      Triple.of(1, 4, 4d),
      Triple.of(0, 1, 5d),
      Triple.of(3, 1, 6d),
      Triple.of(2, 4, 7d));
    var graph = new SparseIntUndirectedWeightedGraph(vertexCount, edges);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SparseIntDirectedGraph")
  @Order(19)
  void testSparseIntDirectedGraph() {
    final Integer vertexCount = 6;
    List<Pair<Integer, Integer>> edges = Arrays.asList(
      Pair.of(0, 5),
      Pair.of(0, 2),
      Pair.of(3, 4),
      Pair.of(1, 4),
      Pair.of(0, 1),
      Pair.of(3, 1),
      Pair.of(2, 4));
    var graph = new SparseIntUndirectedGraph(vertexCount, edges);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }

  @Test
  @DisplayName("SparseIntDirectedWeightedGraph")
  @Order(20)
  void testSparseIntDirectedWeightedGraph() {
    final Integer vertexCount = 6;
    List<Triple<Integer, Integer, Double>> edges = Arrays.asList(
      Triple.of(0, 5, 1d),
      Triple.of(0, 2, 2d),
      Triple.of(3, 4, 3d),
      Triple.of(1, 4, 4d),
      Triple.of(0, 1, 5d),
      Triple.of(3, 1, 6d),
      Triple.of(2, 4, 7d));
    var graph = new SparseIntUndirectedWeightedGraph(vertexCount, edges);

    LOGGER.atInfo().setMessage("{}").addArgument(graph).log();
  }
}
