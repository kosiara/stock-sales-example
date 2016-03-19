package com.bk.stocksales.util;

import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class AssetUtilTestExt extends AssetUtil {
    public static List<Rate> loadRatesFile(Class clazz, int no) {
        try {
            ClassLoader classLoader = clazz.getClassLoader();
            String path = classLoader.getResource("dataset/" + no + "/rates.json").getFile();
            FileInputStream file = new FileInputStream(path);
            return AssetUtil.getRatesFromStream(file);
        } catch (IOException e) {
            System.out.println("Error loading rates file from assets: " + e.toString());
        }
        return null;
    }

    public static List<Transaction> loadTransactionsFile(Class clazz, int no) {
        try {
            ClassLoader classLoader = clazz.getClassLoader();
            String path = classLoader.getResource("dataset/" + no + "/transactions.json").getFile();
            FileInputStream file = new FileInputStream(path);
            return AssetUtil.getTransactionsFromStream(file);
        } catch (IOException e) {
            System.out.println("Error loading rates file from assets: " + e.toString());
        }
        return null;
    }
}
