package com.bk.stocksales.conversion;

import com.bk.stocksales.conversion.model.SaleValueResult;
import com.bk.stocksales.graph.Edge;
import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
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

    public SaleValueResult calculateStockSales(String resultingCurrency, String sku) {

        List<Transaction> filteredList = filterTransactions(sku);
        double totalValue = 0.0d;


        DFSIterative DFSIterative = new DFSIterative(vertices, edges, new Vertex("AUD"), new Vertex("EUR"));
        DFSIterative.getPossibleConversionPath();
        return null;
    }

    public List<Transaction> filterTransactions(final String sku) {
        return Lists.newArrayList(Iterables.filter(transactions, new Predicate<Transaction>() {
            @Override
            public boolean apply(Transaction input) {
                return input.getSku().equals(sku);
            }
        }));
    }



    protected float findConversionForVertices(Vertex source, Vertex dest) {
        float convRate = -1.0f;

        //get direct rate
        for (Edge edge : edges)
            if (edge.getSource().equals(source) && edge.getDestination().equals(dest))
                convRate = edge.getRate();

        if (convRate < 0) {
            DFSIterative DFSIterative = new DFSIterative(vertices, edges, source.cloneVertex(), dest.cloneVertex());
            convRate = new Float(calculateRateForPath(DFSIterative.getPossibleConversionPath()));
        }

        return convRate;
    }

    protected double calculateRateForPath(final List<Vertex> verticesPath) {

        System.out.println("Calculating rate for: " + verticesPath.get(0) + " -> " + verticesPath.get(verticesPath.size() - 1) );

        double resultingConvRate = 1.0d;
        for (int i = 0; i < verticesPath.size() - 1; i++) {
            float convRate = findConversionForVertices(verticesPath.get(i), verticesPath.get(i + 1));
            resultingConvRate *= convRate;
        }

        return resultingConvRate;
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
