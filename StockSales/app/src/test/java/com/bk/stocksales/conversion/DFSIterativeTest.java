package com.bk.stocksales.conversion;

import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.util.AssetUtilTestExt;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class DFSIterativeTest {

    @Test
    public void find_simplest_conversion() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 2);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 2);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);

        DFSIterative DFSIterative = new DFSIterative(salesValueCalc.getVertices(), salesValueCalc.getEdges(), new Vertex("AUD"), new Vertex("EUR"));
        List<Vertex> path = DFSIterative.getPossibleConversionPath();
        assertEquals("AUD->USD->EUR", DFSIterative.convertPathToString(path));


        DFSIterative = new DFSIterative(salesValueCalc.getVertices(), salesValueCalc.getEdges(), new Vertex("EUR"), new Vertex("GBP"));
        path = DFSIterative.getPossibleConversionPath();
        assertEquals("EUR->GBP", DFSIterative.convertPathToString(path));

        DFSIterative = new DFSIterative(salesValueCalc.getVertices(), salesValueCalc.getEdges(), new Vertex("GBP"), new Vertex("USD"));
        path = DFSIterative.getPossibleConversionPath();
        assertEquals("GBP->EUR->USD", DFSIterative.convertPathToString(path) );
    }

    @Test
    public void find_rub_conversion() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 3);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 3);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);

        DFSIterative DFSIterative = new DFSIterative(salesValueCalc.getVertices(), salesValueCalc.getEdges(), new Vertex("AUD"), new Vertex("RUB"));
        List<Vertex> path = DFSIterative.getPossibleConversionPath();
        assertEquals("AUD->USD->PLN->RUB", DFSIterative.convertPathToString(path));

        DFSIterative = new DFSIterative(salesValueCalc.getVertices(), salesValueCalc.getEdges(), new Vertex("RUB"), new Vertex("GBP"));
        path = DFSIterative.getPossibleConversionPath();
        assertEquals("RUB->EUR->GBP", DFSIterative.convertPathToString(path));
    }
}
