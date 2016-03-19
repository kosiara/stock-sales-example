package com.bk.stocksales.rest;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.bk.stocksales.adapter.ItemsRecyclerViewAdapter;
import com.bk.stocksales.conversion.TransactionFilter;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.util.AssetUtil;
import com.google.common.collect.Lists;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class TransactionService {

    private Context context;

    public TransactionService(Context context) {
        this.context = context;
    }

    public void getTransactionsForProduct(final String sku, final RecyclerView recyclerView, final ItemsRecyclerViewAdapter adapter) {
        new Handler().postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    if (sku == null || sku.isEmpty())
                        return;

                    List<Transaction> transactions = AssetUtil.loadTransactionsFile(context, 1);
                    List<Transaction> productTransactions = TransactionFilter.filterTransactions(sku, transactions);

                    List<Item> adapterList = Lists.newArrayList();
                    for(Transaction tran : productTransactions) {
                        String currSymb = Currency.getInstance(tran.getCurrencyCode()).getSymbol();
                        adapterList.add(new Item()
                                .title(currSymb + Float.toString(tran.getAmount()))
                                .subtitle("xxxxxx"));
                    }

                    adapter.addItems(adapterList);
                    recyclerView.setAdapter(adapter);
                }
            }, 75);
    }
}
