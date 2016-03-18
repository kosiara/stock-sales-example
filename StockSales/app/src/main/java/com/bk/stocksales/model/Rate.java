package com.bk.stocksales.model;

/**
 * Created by kosiara on 3/18/16.
 */
public class Rate {

    private String fromCode;
    private String toCode;
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
