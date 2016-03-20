package com.bk.stocksales.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bk.stocksales.DetailsActivity;
import com.bk.stocksales.MainActivity;
import com.bk.stocksales.conversion.SalesValueCalc;
import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.model.view.Item;
import com.bk.stocksales.view.RecyclerItemView;
import com.bk.stocksales.viewmodel.RecyclerViewAdapterBase;
import com.bk.stocksales.viewmodel.ViewWrapper;
import com.google.common.collect.Maps;

import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by bkosarzycki on 03/19/15.
 *
 * Provides data for Activity's RecyclerView
 *
 * This adapter is currently connected with two activities - MainActivity and DetailsActivity
 */
public class ItemsRecyclerViewAdapter extends RecyclerViewAdapterBase<Item, RecyclerItemView>
            implements AdapterView.OnClickListener {

    private Activity mActivity;

    /**
     * Determines whether adapter rows are clickable or not
     */
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
        DetailsActivity.start(mActivity, item);
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

    public ItemsRecyclerViewAdapter activity(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    public ItemsRecyclerViewAdapter isClickingEnabled(boolean clicking) {
        this.mIsClickingEnabled = clicking;
        return this;
    }

    /**
     *  Refreshes item subtitles - i.e. values in pounds
     */
    public void refreshItemsSubtitles(final TextView totalTV, final SalesValueCalc salesValueCalc) {
        ConcurrentMap<Vertex, Float> cache = Maps.newConcurrentMap();
        for (Item item : items) {
            Transaction tran = (Transaction) item.getSubitem();
            double convRate = salesValueCalc.findConversionRateForVerticesWithCache(cache,
                    new Vertex(tran.getCurrencyCode()),
                    new Vertex("GBP"));
            String currSymb = Currency.getInstance("GBP").getSymbol();

            float gdpAmount = new Float(tran.getAmount() * convRate);
            tran.setGdpAmount(gdpAmount);
            item.setSubtitle(currSymb + String.format(Locale.ENGLISH, "%.2f", gdpAmount));
        }

        notifyDataSetChanged();

        ((ItemsRecyclerViewAdapter.RecyclerViewActivity)mActivity).getRecyclerView().postDelayed(
            new Runnable() {
                @Override
                public void run() {
                    refreshTotalTV(totalTV);
                }
            }, 50
        );
    }

    /**
     *  Refreshes TOTAL AMOUNT textview
     */
    private void refreshTotalTV(TextView totalTV) {
        double sum = 0.0d;
        for (Item item: items) {
            Transaction tran = (Transaction) item.getSubitem();
            float gdpAmount = tran.getGdpAmount();
            if (gdpAmount >= 0)
                sum += gdpAmount;
        }
        String prevTxt = totalTV.getText().toString().replace("0.00", "");
        totalTV.setText(prevTxt + Currency.getInstance("GBP").getSymbol() +
                String.format(Locale.ENGLISH, "%.2f", sum));
    }

    public interface RecyclerViewActivity {
        RecyclerView getRecyclerView();
    }
}


