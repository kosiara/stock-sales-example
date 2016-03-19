package com.bk.stocksales.conversion;

import com.bk.stocksales.conversion.model.ConversionResult;
import com.bk.stocksales.graph.Edge;
import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by kosiara on 3/18/16.
 */
public class SalesValueCalc {

    List<Vertex> vertices = Lists.newArrayList();
    List<Edge> edges = Lists.newArrayList();
    List<Transaction> transactions;

    public SalesValueCalc(List<Rate> rates, List<Transaction> trans) {
        for (Rate rate : rates)
            edges.add(new Edge(rate).cloneEdge());
        transactions = trans;

        List<Vertex> vert = Lists.newArrayList();
        for(Edge edge : edges) {
            vert.add(edge.getSource().cloneVertex());
            vert.add(edge.getDestination().cloneVertex());
        }

        for (Vertex v : ImmutableSet.copyOf(vert).asList())
            vertices.add(v.cloneVertex());
    }

    public ConversionResult calculateStockSales(String resultingCurrency, String sku) {
        DFSIterative DFSIterative = new DFSIterative(vertices, edges, new Vertex("AUD"), new Vertex("EUR"));
        DFSIterative.getPossibleConversionPath();
        return null;
    }

    public List<Edge> cloneEdges() {
        List<Edge> edgeList = Lists.newArrayList();
        for (Edge e : edges)
            edgeList.add(e.cloneEdge());
        return edgeList;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
