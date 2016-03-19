package com.bk.stocksales;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bk.stocksales.adapter.ItemsRecyclerViewAdapter;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.rest.TransactionService;
import com.bk.stocksales.view.RecyclerItemView;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by bkosarzycki on 3/18/16.
 */
public class DetailsActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeToRefresh;
    ItemsRecyclerViewAdapter mAdapter;
    TransactionService mTransactionsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ItemsRecyclerViewAdapter();
        mTransactionsService = new TransactionService(this);
        ButterKnife.bind(this);


        initializeRecyclerView();

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                setRefreshing(false);
            }
        });
    }

    private void initializeRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTransactionsService.getTransactions(mRecyclerView, mAdapter);
    }

    private void setRefreshing(final boolean val) {
        mSwipeToRefresh.post(new Runnable() {
            @Override public void run() {
                mSwipeToRefresh.setRefreshing(val);
                if (!val)
                    Snackbar.make(DetailsActivity.this.mRecyclerView, "Refreshing not implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); }
        });
    }

    public static void start(Activity activity, Item item) {
        Intent myIntent = new Intent(activity, DetailsActivity.class);
        myIntent.putExtra("sku", item.getTitle());
        activity.startActivity(myIntent);
    }
}
