package com.fox.david.ATM.utils;

import org.jboss.logging.Logger;

import java.util.LinkedHashMap;

public class CurrencyUtil {
    static Logger LOGGER = Logger.getLogger(CurrencyUtil.class);

    //Util method to populate the currency HashMap
    public static LinkedHashMap<String, Integer> setUpCurrency(int fifties, int twenties, int tens, int fives) {
        LOGGER.debug("Constructing currency storage");
        LinkedHashMap<String, Integer> hashmap = new LinkedHashMap<>();
        hashmap.put("50", fifties);
        hashmap.put("20", twenties);
        hashmap.put("10", tens);
        hashmap.put("5", fives);

        int total = (50 * fifties) + (20 * twenties) + (10 * tens) + (5 * fives);

        hashmap.put("total", total);
        return hashmap;
    }
}
