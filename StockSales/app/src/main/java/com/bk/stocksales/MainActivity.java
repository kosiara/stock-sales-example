package com.bk.stocksales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bk.stocksales.conversion.SalesValueCalc;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.util.AssetUtil;

import java.util.List;

/**
 * created by bkosarzycki on 3/18/16.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Rate> rates = AssetUtil.loadRatesFile(this, 2);
        List<Transaction> transactions = AssetUtil.loadTransactionsFile(this, 1);
        SalesValueCalc salesValueCalc = new SalesValueCalc(rates, transactions);
        salesValueCalc.calculateStockSales("", "");
    }
}
