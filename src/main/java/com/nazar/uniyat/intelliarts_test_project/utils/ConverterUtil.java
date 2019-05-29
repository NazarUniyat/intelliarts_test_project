package com.nazar.uniyat.intelliarts_test_project.utils;

import com.nazar.uniyat.intelliarts_test_project.components.RatesMapper;

import java.util.Map;

public class ConverterUtil {

    public static Double convertToEUR(RatesMapper ratesMapper, String currency, Double cost) {
        Map<String, Double> data = ratesMapper.getData();
        Double aDouble = data.get(currency);
        Double converted = cost / aDouble;
        return converted;
    }


}
