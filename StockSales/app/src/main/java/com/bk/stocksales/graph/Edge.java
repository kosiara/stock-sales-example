package com.bk.stocksales.graph;

import com.bk.stocksales.model.Rate;

/**
 * Created by bkosarzycki on 3/18/16.
 *
 * Graph's edge. Has source and destination.
 * Rate represents edge's weight.
 *
 */
public class Edge {

    private Vertex source;
    private Vertex destination;
    private float rate;

    public Edge(Vertex source, Vertex destination, float rate) {
        this.source = source;
        this.destination = destination;
        this.rate = rate;
    }

    public Edge(Rate rate) {
        this.source = new Vertex(rate.getFromCode());
        this.destination = new Vertex(rate.getToCode());
        this.rate = rate.getRate();
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public float getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;
        if (!source.equals(edge.source)) return false;
        return destination.equals(edge.destination);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }

    public Edge cloneEdge() {
        return new Edge(source.cloneVertex(), destination.cloneVertex(), rate);
    }
}
