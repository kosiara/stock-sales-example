package com.bk.stocksales.rest;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.bk.stocksales.adapter.ItemsRecyclerViewAdapter;
import com.bk.stocksales.conversion.SalesValueCalc;
import com.bk.stocksales.conversion.TransactionFilter;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.util.AssetUtil;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class ProductsService {

    private Context context;

    public ProductsService(Context context) {
        this.context = context;
    }

    public void getProducts(final RecyclerView recyclerView, final ItemsRecyclerViewAdapter adapter) {
        new Handler().postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    List<Transaction> transactions = AssetUtil.loadTransactionsFile(context, 1);
                    List<String> skuList = TransactionFilter.getUniqueSku(transactions);

                    List<Item> adapterList = Lists.newArrayList();
                    for(String sku : skuList) {
                        int noOfTransaction = TransactionFilter.filterTransactions(sku, transactions).size();
                        adapterList.add(new Item().title(sku).subtitle(Integer.toString(noOfTransaction)));
                    }

                    //todo: [impr] use lambda expression from Java8 insted of reference passing
                    adapter.addItems(adapterList);
                    recyclerView.setAdapter(adapter);
                }
            }, 75);
    }
}
