package com.bk.stocksales.model.view;

/**
 * Created by bkosarzycki on 03/19/15.
 */
public class Item {

    /**
     * Represents visible fields
     */
    private String title;
    private String subtitle;

    /**
     * Holds additional data object for each row
     */
    private Object subItem;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Item title(String title) {
        this.title = title;
        return this;
    }

    public Item subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public Object getSubitem() {
        return subItem;
    }

    public void setSubitem(Object item) {
        this.subItem = item;
    }

    public Item item(Object obj) {
        subItem = obj;
        return this;
    }
}
