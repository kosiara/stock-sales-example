package com.bk.stocksales.conversion;

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

    List<Vertex> vertices;
    List<Edge> edges = Lists.newArrayList();
    List<Transaction> transactions;

    //implement cache here

    public SalesValueCalc(List<Rate> rates, List<Transaction> trans) {
        for (Rate rate : rates)
            edges.add(new Edge(rate));
        transactions = trans;

        List<Vertex> vert = Lists.newArrayList();
        for(Edge edge : edges) {
            vert.add(edge.getSource());
            vert.add(edge.getDestination());
        }

        vertices = ImmutableSet.copyOf(vert).asList();
    }

    public ConversionResult calculateStockSales(String resultingCurrency, String sku) {
        DFSIterative DFSIterative = new DFSIterative(vertices, edges, new Vertex("AUD"), new Vertex("EUR"));
        DFSIterative.calculate();
        return null;
    }
}
