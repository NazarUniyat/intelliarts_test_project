package com.nazar.uniyat.intelliarts_test_project.testData;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestData {

    public static List<Purchase> createPurchases() {
        List<Purchase> purchases = Arrays.asList(
                new Purchase(1, "2019-05-22", 10, "UAH", "first"),
                new Purchase(2, "2019-05-21", 20, "USD", "second"),
                new Purchase(3, "2019-05-29", 30, "EUR", "aaa"),
                new Purchase(4, "2018-08-29", 100, "PLN", "bbb"),
                new Purchase(5, "2018-08-29", 3000, "UAH", "ccc")
        );
        return purchases;
    }


    public static List<Purchase> createPurchasesWithoutId() {
        List<Purchase> purchases = Arrays.asList(
                new Purchase( "2019-05-22", 10, "UAH", "first"),
                new Purchase( "2019-05-21", 20, "USD", "second"),
                new Purchase( "2019-05-29", 30, "EUR", "aaa"),
                new Purchase( "2018-08-29", 100, "PLN", "bbb"),
                new Purchase( "2018-08-29", 3000, "UAH", "ccc"),
                new Purchase( "2018-05-29", 3000, "UAH", "cccds")
        );
        return purchases;
    }

    public static List<Purchase> createPurchasesListWithTheSameDate() {
        List<Purchase> purchases = Arrays.asList(
                new Purchase(1, "2018-08-29", 100, "PLN", "bbb"),
                new Purchase(2, "2018-08-29", 3000, "UAH", "ccc")
        );
        return purchases;
    }

    public static HashMap<String, Double> createMapWithSelectedCurrency(String currency) {
        HashMap<String, Double> stringDoubleHashMap = new HashMap<>();
        stringDoubleHashMap.put(currency, 29.5);
        return stringDoubleHashMap;
    }

    public static HashMap<String, Double> createCurrencyMap() {
        HashMap<String, Double> stringDoubleHashMap = new HashMap<>();
        stringDoubleHashMap.put("EUR", 1.0);
        stringDoubleHashMap.put("UAH", 29.5);
        stringDoubleHashMap.put("USD", 1.1);
        stringDoubleHashMap.put("PLN", 4.2);
        stringDoubleHashMap.put("AZN", 1.9);
        return stringDoubleHashMap;

    }

}
