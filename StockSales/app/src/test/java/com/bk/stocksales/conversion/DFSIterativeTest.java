package com.bk.stocksales.conversion;

import com.bk.stocksales.model.Rate;
import com.bk.stocksales.model.Transaction;
import com.bk.stocksales.util.AssetUtilTestExt;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by bkosarzycki on 3/19/16.
 */
public class DFSIterativeTest {

    @Test
    public void find_simplest_conversion() throws Exception {
        List<Transaction> transactions = AssetUtilTestExt.loadTransactionsFile(this.getClass(), 2);
        List<Rate> rates = AssetUtilTestExt.loadRatesFile(this.getClass(), 2);

        int aa = 1;
    }


}
