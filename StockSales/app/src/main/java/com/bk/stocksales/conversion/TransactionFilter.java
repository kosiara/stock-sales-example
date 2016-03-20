package com.bk.stocksales.conversion;

import com.bk.stocksales.model.Transaction;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by bkosarzycki on 3/19/16.
 *
 * Can filter transactions based on a single SKU, as well as get list of unique SKUs.
 */
public class TransactionFilter {

    public static List<Transaction> filterTransactions(final String sku, List<Transaction> trans) {
        return Lists.newArrayList(Iterables.filter(trans, new Predicate<Transaction>() {
            @Override
            public boolean apply(Transaction input) {
                return input.getSku().equals(sku);
            }
        }));
    }

    public static List<String> getUniqueSku(List<Transaction> trans) {
        List<String> output = Lists.newArrayList();
        for (Transaction tran : trans)
            output.add(tran.getSku());

        return ImmutableSet.copyOf(output).asList();
    }
}
