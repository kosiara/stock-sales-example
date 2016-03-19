package com.bk.stocksales.util;

import android.content.Context;
import android.util.Log;

import com.bk.stocksales.conversion.exception.MinusExchangeRate;
import com.bk.stocksales.graph.Vertex;
import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by bkosarzycki on 3/18/16.
 */
public class AssetUtil {

    public static List<Rate> loadRatesFile(Context context, int no) throws MinusExchangeRate {
        try {
            InputStream stream = context.getAssets().open("dataset/" + no + "/rates.json");
            return getRatesFromStream(stream);
        } catch (IOException e) {
            Log.e(AssetUtil.class.getSimpleName(), "Error loading rates file from assets: " + e.toString());
        }

        return null;
    }

    public static List<Transaction> loadTransactionsFile(Context context, int no) {
        try {
            InputStream stream = context.getAssets().open("dataset/" + no + "/transactions.json");
            return getTransactionsFromStream(stream);
        } catch (IOException e) {
            Log.e(AssetUtil.class.getSimpleName(), "Error loading rates file from assets: " + e.toString());
        }
        return null;
    }

    public static List<Rate> getRatesFromStream(InputStream stream) throws UnsupportedEncodingException, MinusExchangeRate {
        Type collectionType = new TypeToken<Collection<Rate>>(){}.getType();
        List<Rate> list =  new Gson().fromJson(new InputStreamReader(stream, "UTF-8") , collectionType);
        for (Rate rate : list) {
            if (rate.getRate() <= 0)
                throw new MinusExchangeRate(new Vertex(rate.getFromCode()), new Vertex(rate.getToCode()));

        }
        return list;
    }

    public static List<Transaction> getTransactionsFromStream(InputStream stream) throws UnsupportedEncodingException {
        Type collectionType = new TypeToken<Collection<Transaction>>(){}.getType();
        return new Gson().fromJson(new InputStreamReader(stream, "UTF-8") , collectionType);
    }
}
