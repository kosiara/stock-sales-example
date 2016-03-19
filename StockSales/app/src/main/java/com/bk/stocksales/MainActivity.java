package com.bk.stocksales;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bk.stocksales.adapter.ItemsRecyclerViewAdapter;
import com.bk.stocksales.conversion.SalesValueCalc;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.rest.ProductsService;
import com.bk.stocksales.util.AssetUtil;
import com.google.common.collect.Lists;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * created by bkosarzycki on 3/18/16.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh) SwipeRefreshLayout mSwipeToRefresh;
    ItemsRecyclerViewAdapter mAdapter;
    ProductsService mProductService;

    /**
     * Sample data set number
     */
    public static final int DATASET_NO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ItemsRecyclerViewAdapter().mainActivity(this);
        mProductService = new ProductsService(this);
        ButterKnife.bind(this);

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
        mProductService.getProducts(mRecyclerView, mAdapter);
    }

    private void setRefreshing(final boolean val) {
        mSwipeToRefresh.post(new Runnable() {
            @Override public void run() {
                mSwipeToRefresh.setRefreshing(val);
                if (!val)
                    Snackbar.make(MainActivity.this.mRecyclerView, "Refreshing not implemented", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); }
        });
    }
}
