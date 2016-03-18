package com.bk.stocksales.graph;

/**
 * Created by bkosarzycki on 3/18/16.
 */
public class Vertex {
    private String currCode;

    public Vertex(String currCode) {
        this.currCode = currCode;
    }

    public String getCurrCode() {
        return currCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;
        return currCode.equals(vertex.currCode);
    }

    @Override
    public int hashCode() {
        return currCode.hashCode();
    }
}
