package com.bk.stocksales.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kosiara on 3/18/16.
 */
public class Transaction {

    private String sku;
    private float amount;
    @SerializedName("currency") private String currencyCode;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
