package com.bk.stocksales.rest;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.bk.stocksales.DetailsActivity;
import com.bk.stocksales.MainActivity;
import com.bk.stocksales.adapter.ItemsRecyclerViewAdapter;
import com.bk.stocksales.conversion.TransactionFilter;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.util.AssetUtil;
import com.google.common.collect.Lists;

import java.util.Currency;
import java.util.List;

/**
 * Created by bkosarzycki on 3/19/16.
 *
 * Represents REST call for transactions.
 *
 * todo: replace with REST calls
 */
public class TransactionService {

    private DetailsActivity detailsActivity;

    public TransactionService(DetailsActivity activity) {
        this.detailsActivity = activity;
    }

    public void getTransactionsForProduct(final String sku, final RecyclerView recyclerView, final ItemsRecyclerViewAdapter adapter) {
        new Handler().postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    if (sku == null || sku.isEmpty())
                        return;

                    List<Transaction> transactions = AssetUtil.loadTransactionsFile(detailsActivity, MainActivity.DATASET_NO);
                    List<Transaction> productTransactions = TransactionFilter.filterTransactions(sku, transactions);

                    List<Item> adapterList = Lists.newArrayList();
                    for(Transaction tran : productTransactions) {
                        String currSymb = Currency.getInstance(tran.getCurrencyCode()).getSymbol();
                        adapterList.add(new Item()
                                .title(currSymb + Float.toString(tran.getAmount()))
                                .subtitle("-------")
                                .item(tran)
                        );
                    }

                    adapter.addItems(adapterList);
                    recyclerView.setAdapter(adapter);
                    detailsActivity.postCurrConversionJob(adapter, productTransactions);
                }
            }, 75);
    }
}
