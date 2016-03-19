package com.bk.stocksales.conversion.exception;

import com.bk.stocksales.graph.Vertex;

/**
 * Created by kosiara on 3/19/16.
 */
public class MinusExchangeRate extends Exception {
    public MinusExchangeRate(Vertex source, Vertex dest) {
        super("There was a minus exchange rate in the graph for currency : " +
                (source != null ? source.getCurrCode() : "<unknown>") + "->" +
                (dest != null ? dest.getCurrCode() : "<unknown>"));
    }
}
