package com.bk.stocksales.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kosiara on 3/18/16.
 */
public class Transaction {

    private String sku;
    private float amount;
    @SerializedName("currency") private String currencyCode;

    /**
     * Holds converted GDP amount
     */
    private float gdpAmount = -1.00f;

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

    public float getGdpAmount() {
        return gdpAmount;
    }

    public void setGdpAmount(float gdpAmount) {
        this.gdpAmount = gdpAmount;
    }
}
