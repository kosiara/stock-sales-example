package com.bk.stocksales.conversion;

import com.bk.stocksales.graph.Edge;
import com.bk.stocksales.graph.Vertex;
import com.google.common.collect.Lists;

import java.util.HashMap;
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

    public DFSIterative(List<Vertex> vertices, List<Edge> edges, Vertex from, Vertex to) {
        sourceVertex = from;
        destVertex = to;
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Vertex> getPossibleConversionPath() {
        List<List<Vertex>> conversionPaths = Lists.newArrayList();

        VStack stack = new VStack();
        HashMap<Vertex, Vertex> parentMap = new HashMap<Vertex, Vertex>();
        HashMap<Vertex, Vertex> alreadyTriedConv = new HashMap<Vertex, Vertex>();
        stack.push(sourceVertex);

        boolean found = false;

        while (!stack.empty() && !found) {
            Vertex current = stack.pop();
            if  (!current.isDiscovered()) {
                current.setDiscovered(true);
                for(Vertex adjVer : findAdjacentVertices(current)) {
                    if (isSameOrInverseConversion(current, adjVer, alreadyTriedConv) || found)
                        continue;

                    stack.push(adjVer);
                    parentMap.put(adjVer, current);
                    alreadyTriedConv.put(current, adjVer);

                    if (adjVer.equals(destVertex)) {
                        List<Vertex> conversionPath = findConversionPath(sourceVertex, destVertex, parentMap);
                        conversionPaths.add(conversionPath);
                        System.out.println("FOUND - " + adjVer + ", PATH: " + convertPathToString(conversionPath) );

                        //TODO: loop in finding parent in this solution - change it
                        found = true;
                    }
                }
            }
        }

        int minLength = Integer.MAX_VALUE;
        int indx = 0;
        for (int i = 0; i < conversionPaths.size(); i++) {
            if (conversionPaths.get(i).size() < minLength ) {
                minLength = conversionPaths.get(i).size();
                indx = i;
            }
        }

        return conversionPaths.get(indx);
    }

    private boolean isSameOrInverseConversion(Vertex parent, Vertex child, HashMap<Vertex, Vertex> alreadyTriedConv) {
        for (HashMap.Entry<Vertex, Vertex> entry : alreadyTriedConv.entrySet()) {
            if ((entry.getKey().equals(parent) && entry.getValue().equals(child))
                || (entry.getValue().equals(parent) && entry.getKey().equals(child)))
                return true;
        }
        return false;
    }



    private List<Vertex> findConversionPath(Vertex sourceVertex, Vertex destVertex, HashMap<Vertex, Vertex> parentMap) {
        List<Vertex> path = Lists.newArrayList();
        path.add(destVertex);

        Vertex curr = parentMap.get(destVertex);
        while (!curr.equals(sourceVertex)) {
            path.add(curr);
            curr = parentMap.get(curr);
        }
        path.add(sourceVertex);

        path = Lists.reverse(path);
        return path;
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

    public static String convertPathToString(List<Vertex> conversionPath ) {
        String output = "";
        for (Vertex v: conversionPath)
            output += v + "->";

        if (output.endsWith("->"))
            output = output.substring(0, output.length() - 2);

        return output;
    }


    private class VStack extends Stack<Vertex> {
        @Override
        public Vertex push(Vertex vertex) {
            System.out.println(vertex + " - ");
            return super.push(vertex);
        }
    }
}
