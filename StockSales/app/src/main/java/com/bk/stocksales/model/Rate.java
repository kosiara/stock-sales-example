package com.bk.stocksales.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bkosarzycki on 3/18/16.
 *
 * Corresponds to currency exchange rate e.g.  EUR -> USD  1.2
 */
public class Rate {

    @SerializedName("from") private String fromCode;
    @SerializedName("to") private String toCode;
    private float rate;

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
