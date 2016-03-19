package com.bk.stocksales.conversion;

import com.bk.stocksales.conversion.model.SaleValueResult;
import com.bk.stocksales.conversion.model.SaleValueResultType;
import com.bk.stocksales.graph.Edge;
import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ExecutionError;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

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

    public SaleValueResult calculateStockSalesInGBP(String sku, boolean abortOnError) throws Exception {

        ConcurrentMap<Vertex, Float> cache = Maps.newConcurrentMap();

        double totalValue = 0.0d;
        final Vertex GBP = new Vertex("GBP");
        SaleValueResultType type = SaleValueResultType.FULL;

        List<Transaction> filteredList = filterTransactions(sku);
        for (Transaction tran : filteredList) {
            float amount = tran.getAmount();
            Vertex currency =  new Vertex(tran.getCurrencyCode());
            if (currency.equals(GBP))
                totalValue += amount;
            else {
                double rate = findConversionRateForVerticesWithCache(cache, currency, GBP);
                if (rate == -1) {
                    if (abortOnError)
                        throw new Exception("Invalid conversion rate!");
                    else {
                        type = SaleValueResultType.INCOMPLETE;
                        continue;
                    }
                }
                totalValue += amount * rate;
            }
        }

        SaleValueResult res = new SaleValueResult();
        res.currency = GBP.getCurrCode();
        res.value = new Float(totalValue);
        res.saleValueResultType = type;

        return res;
    }

    private double findConversionRateForVerticesWithCache(ConcurrentMap<Vertex, Float> cache, Vertex currency, Vertex gbp) {
        if (cache.containsKey(currency))
            return cache.get(currency);

        double val = findConversionRateForVertices(currency, gbp);
        cache.put(currency, new Float(val));
        return val;
    }

    public List<Transaction> filterTransactions(final String sku) {
        return Lists.newArrayList(Iterables.filter(transactions, new Predicate<Transaction>() {
            @Override
            public boolean apply(Transaction input) {
                return input.getSku().equals(sku);
            }
        }));
    }

    protected float findConversionRateForVertices(Vertex source, Vertex dest) {
        float convRate = -1.0f;

        if (source.equals(dest))
            return 1.0f;

        //get direct rate
        for (Edge edge : edges) {
            edge.getSource().setDiscovered(false); edge.getDestination().setDiscovered(false);
            if (edge.getSource().equals(source) && edge.getDestination().equals(dest))
                convRate = edge.getRate();
        }

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
            float convRate = findConversionRateForVertices(verticesPath.get(i), verticesPath.get(i + 1));
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
