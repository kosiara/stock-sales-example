package com.bk.stocksales;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bk.stocksales.conversion.SalesValueCalc;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.util.AssetUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by bkosarzycki on 3/18/16.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeToRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
//        List<Rate> rates = AssetUtil.loadRatesFile(this, 2);
//        List<Transaction> transactions = AssetUtil.loadTransactionsFile(this, 1);

        initializeRecyclerView();

        mSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                //todo: redownload data
                setRefreshing(false);
            }
        });
    }

    private void initializeRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setAdapter(mAdapter);
    }

    private void setRefreshing(final boolean val) {
        mSwipeToRefresh.post(new Runnable() {
            @Override public void run() { mSwipeToRefresh.setRefreshing(val); }
        });
    }
}
