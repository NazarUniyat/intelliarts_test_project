package com.nazar.uniyat.intelliarts_test_project.utils;

import com.nazar.uniyat.intelliarts_test_project.components.RatesMapper;
import com.nazar.uniyat.intelliarts_test_project.testData.TestData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConverterUtilTest {

    @Test
    public void givenSomePriceInArbitraryCurrency_whenConvertToEUR_thenReturnPriceConvertedToEur() {
        HashMap<String, Double> currencyMap = TestData.createCurrencyMap();
        RatesMapper ratesMapper = new RatesMapper();
        ratesMapper.setData(currencyMap);
        Double uah = ConverterUtil.convertToEUR(ratesMapper, "UAH", 59.00);
        assertEquals(new Double(2.00), uah);
    }
}