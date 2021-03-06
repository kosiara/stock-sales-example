package com.bk.stocksales.graph;

/**
 * Created by bkosarzycki on 3/18/16.
 *
 * Single graph's node.
 */
public class Vertex {
    private String currCode;

    /**
     * Tells DFS whether this node had been already been visited or not.
     */
    private boolean discovered;

    public Vertex(String currCode) {
        this.currCode = currCode;
    }

    public String getCurrCode() {
        return currCode;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
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

    @Override
    public String toString() {
        return this.getCurrCode();
    }

    public Vertex cloneVertex() {
        return new Vertex(this.currCode);
    }
}
