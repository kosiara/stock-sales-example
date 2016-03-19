package com.bk.stocksales.conversion;

import com.bk.stocksales.conversion.model.SaleValueResult;
import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.util.AssetUtilTestExt;
import com.google.common.collect.Lists;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class SalesValueCalcTest {


    @Test
    public void filter_transactions() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 1);
        SalesValueCalc salesValueCalc = new SalesValueCalc(Lists.<Rate>newArrayList(), transactions);
        List<Transaction> filteredList = salesValueCalc.filterTransactions("R9704");

        assertEquals(451,filteredList.size());
    }

    @Test
    public void find_trans_rate_for_path() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 2);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 2);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);
        double convRate = salesValueCalc.calculateRateForPath(
                Lists.<Vertex>newArrayList(
                    new Vertex[] {
                            new Vertex("AUD"), new Vertex("USD"), new Vertex("EUR")
                    }
                )
        );
        assertEquals(0.25d, convRate, 0.001);

        salesValueCalc = new SalesValueCalc(rates, transactions);
        convRate = salesValueCalc.calculateRateForPath(
                Lists.<Vertex>newArrayList(
                        new Vertex[] {
                                new Vertex("USD"), new Vertex("EUR"), new Vertex("GBP")
                        }
                )
        );
        assertEquals(0.25d, convRate, 0.001);
    }

    @Test
    public void find_direct_trans_rate() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 2);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 2);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);
        float convRate = salesValueCalc.findConversionRateForVertices(new Vertex("EUR"), new Vertex("GBP"));
        assertEquals(0.5f, convRate, 0.001);

        convRate = salesValueCalc.findConversionRateForVertices(new Vertex("USD"), new Vertex("AUD"));
        assertEquals(2.0f, convRate, 0.001);
    }

    @Test
    public void find_indirect_trans_rate() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 2);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 2);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);
        float convRate = salesValueCalc.findConversionRateForVertices(new Vertex("USD"), new Vertex("GBP"));
        assertEquals(0.25f, convRate, 0.001);

        convRate = salesValueCalc.findConversionRateForVertices(new Vertex("EUR"), new Vertex("AUD"));
        assertEquals(4.0f, convRate, 0.001);
    }

    @Test
    public void find_simplest_calculation() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 2);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 2);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);
        SaleValueResult result = salesValueCalc.calculateStockSalesInGBP("J4064", true);
        assertEquals(1.875f, result.value, 0.001);
    }

    @Test
    public void calculation_heavy_load() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 1);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 1);

        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);
        SaleValueResult result = salesValueCalc.calculateStockSalesInGBP("O7730", true);
        assertEquals(9780.62f, result.value, 0.001);
    }
}
