# JGraphT

## Graph Structures

| Class Name                     | Edges      | Self-loops | Multiple edges | Weighted |
|--------------------------------|------------|------------|----------------|----------|
| SimpleGraph                    | undirected | no         | no             | no       |
| Multigraph                     | undirected | no         | yes            | no       |  
| Pseudograph                    | undirected | yes        | yes            | no       |
| DefaultUndirectedGraph         | undirected | yes        | no             | no       |
| SimpleWeightedGraph            | undirected | no         | no             | yes      |
| WeightedMultigraph             | undirected | no         | yes            | yes      |
| WeightedPseudograph            | undirected | yes        | yes            | yes      |
| DefaultUndirectedWeightedGraph | undirected | yes        | no             | yes      |
| SimpleDirectedGraph            | directed   | no         | no             | no       |
| DirectedMultigraph             | directed   | no         | yes            | no       |
| DirectedPseudograph            | directed   | yes        | yes            | no       |
| DefaultDirectedGraph           | directed   | yes        | no             | no       |
| SimpleDirectedWeightedGraph    | directed   | no         | no             | yes      |
| DirectedWeightedMultigraph     | directed   | no         | yes            | yes      |
| DirectedWeightedPseudograph    | directed   | yes        | yes            | yes      |
| DefaultDirectedWeightedGraph   | directed   | yes        | no             | yes      |

The structural properties are as follows:

- undirected edges: an edge simply connects a vertex pair, without imposing a direction
- directed edges: an edge has a source and a target
- self-loops: whether to allow edges which connect a vertex to itself
- multiple edges: whether to allow more than one edge between the same vertex pair (note that in a directed graph, two edges between the same vertex pair but with opposite direction do not count as multiple edges)
- weighted: whether a double weight is associated with each edge (for these graph types, you’ll usually want to use DefaultWeightedEdge as your edge class); unweighted graphs are treated as if they have a uniform edge weight of 1.0, which allows them to be used in algorithms such as finding a shortest path

The `GraphType` interface allows you to access this metadata for an existing graph instance (using the `getType` accessor).

You can also use `GraphTypeBuilder` to instantiate a new graph without directly constructing a concrete class:

```java
    private static Graph<Integer, DefaultEdge> buildEmptySimpleGraph()
    {
        return GraphTypeBuilder
            .<Integer, DefaultEdge> undirected().allowingMultipleEdges(false)
            .allowingSelfLoops(false).edgeClass(DefaultEdge.class).weighted(false).buildGraph();
    }
```

`GraphTypeBuilder` uses the property values you supply in order to automatically choose the correct concrete class for you. This is generally a cleaner pattern to follow, but it’s not applicable if you end up needing to subclass one of the provided graph classes.

