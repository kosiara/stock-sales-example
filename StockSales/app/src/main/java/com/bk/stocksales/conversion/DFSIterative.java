package com.bk.stocksales.conversion;

import com.bk.stocksales.graph.Edge;
import com.bk.stocksales.graph.Vertex;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Stack;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class DFSIterative {

    Vertex sourceVertex;
    Vertex destVertex;
    List<Vertex> vertices;
    List<Edge> edges;

    //remember adjacent vertices

    public DFSIterative(List<Vertex> vertices, List<Edge> edges, Vertex from, Vertex to) {
        sourceVertex = from;
        destVertex = to;
        this.vertices = vertices;
        this.edges = edges;
    }

    public void calculate() {
        VStack stack = new VStack();
        stack.push(sourceVertex);

        while (!stack.empty()) {
            Vertex current = stack.pop();
            if  (!current.isDiscovered()) {
                current.setDiscovered(true);
                for(Vertex adjVer : findAdjacentVertices(current)) {
                    stack.push(adjVer);
                    if (adjVer.equals(destVertex))
                        System.out.println("FOUND - " + adjVer);
                }
            }
        }

    }

    private int findVertexIndex(Vertex vert) {
        for (int i = 0; i < vertices.size() ; i++)
            if (vertices.get(i).equals(vert))
                return i;
        return -1;
    }

    private List<Vertex> findAdjacentVertices(Vertex vertex) {

        List<Vertex> adjacent = Lists.newArrayList();
        for(Edge edge : edges) {
            if (edge.getSource().equals(vertex))
                adjacent.add(edge.getDestination());
        }

        return adjacent;
    }


    private class VStack extends Stack<Vertex> {
        @Override
        public Vertex push(Vertex vertex) {
            System.out.println(vertex + " - ");
            return super.push(vertex);
        }
    }
}
