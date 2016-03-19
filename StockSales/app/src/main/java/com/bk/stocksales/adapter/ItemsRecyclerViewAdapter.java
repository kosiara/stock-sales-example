package com.bk.stocksales.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bk.stocksales.DetailsActivity;
import com.bk.stocksales.MainActivity;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.view.RecyclerItemView;
import com.bk.stocksales.viewmodel.RecyclerViewAdapterBase;
import com.bk.stocksales.viewmodel.ViewWrapper;

import java.util.List;

/**
 * Created by bkosarzycki on 03/19/15.
 *
 * Provides data for MainActivity's RecyclerView
 */
public class ItemsRecyclerViewAdapter extends RecyclerViewAdapterBase<Item, RecyclerItemView>
            implements AdapterView.OnClickListener {

    private Activity mMainActivity;
    private boolean mIsClickingEnabled = true;

    @Override
    public void onBindViewHolder(ViewWrapper<RecyclerItemView> viewHolder, int position) {
        RecyclerItemView view = viewHolder.getView();

        final Item msg = items.get(position);
        view.bind(msg, position, this);
    }

    @Override
    public void onClick(View v) {
        if (!mIsClickingEnabled)
            return;

        RecyclerItemView view = (RecyclerItemView)v;
        Item item = view.getItem();
        DetailsActivity.start(mMainActivity, item);
    }

    @Override
    protected RecyclerItemView onCreateItemView(ViewGroup parent, int viewType) {
        RecyclerItemView v = new RecyclerItemView(parent.getContext(), null);
        return v;
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (observer != null)
            super.unregisterAdapterDataObserver(observer);
    }

    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<Item> itemsToAdd) {
        if (itemsToAdd != null)
            items.addAll(itemsToAdd);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public ItemsRecyclerViewAdapter mainActivity(MainActivity activity) {
        this.mMainActivity = activity;
        return this;
    }

    public ItemsRecyclerViewAdapter isClickingEnabled(boolean clicking) {
        this.mIsClickingEnabled = clicking;
        return this;
    }
}


