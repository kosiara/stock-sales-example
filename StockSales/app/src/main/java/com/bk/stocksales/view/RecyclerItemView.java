package com.bk.stocksales.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bk.stocksales.model.view.Item;

/**
 * Created by bkosarzycki on 03/19/15.
 *
 * MVVM view for RecyclerView's item.
 */
public class RecyclerItemView extends FrameLayout {

    private Context mContext;
    private Item item;

    public RecyclerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        removeAllViews();
        addView(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null, true));
    }

    public void bind(Item item, int position, OnClickListener clickListener) {
        this.item = item;

        TextView nameTextView = (TextView) this.findViewById(android.R.id.text1);
        TextView subtitleTextView = (TextView) this.findViewById(android.R.id.text2);

        nameTextView.setText(item.getTitle());
        subtitleTextView.setText(item.getSubtitle());

        if (clickListener != null)
            this.setOnClickListener(clickListener);
    }

    public Item getItem() {
        return item;
    }
}
