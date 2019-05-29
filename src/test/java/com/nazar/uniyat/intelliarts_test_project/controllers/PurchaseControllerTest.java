package com.nazar.uniyat.intelliarts_test_project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.nazar.uniyat.intelliarts_test_project.components.RatesMapper;
import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.repository.PurchaseRepository;
import com.nazar.uniyat.intelliarts_test_project.service.PurchaseService;
import com.nazar.uniyat.intelliarts_test_project.testData.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseRepository purchaseRepository;

    @MockBean
    RatesMapper ratesMapper;


    @Test
    public void giveURIThatContainsDateInInvalidFormat_whenSave_thenReturnValidationException() throws Exception {

        this.mockMvc.perform(post("/save/2010-111-11/100/USD/test")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.name").value("save.date: wrong count of characters in date format"));
    }

    @Test
    public void giveURIThatContainsNegativePrice_whenSave_thenReturnValidationException() throws Exception {

        this.mockMvc.perform(post("/save/2010-11-11/-100/USD/test")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.name").value("save.price: only positive numbers!!!"));
    }

    @Test
    public void giveURIWhereCurrencyContainsMorOrLessThen3Characters_whenSave_thenReturnValidationException() throws Exception {

        this.mockMvc.perform(post("/save/2010-11-11/100/USDF/test")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.name").value("save.currency: wrong input, more or less then 3 characters"));
    }

    @Test
    public void giveURIWhereNameLessThen2Characters_whenSave_thenReturnValidationException() throws Exception {

        this.mockMvc.perform(post("/save/2010-11-11/100/USD/t")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.name").value("save.name: name of goods should be more than 2 characters and less then 50"));
    }

    @Test
    public void giveURIWithValidParams_whenSave_thenReturnResult() throws Exception {
        when(ratesMapper.getData()).thenReturn(TestData.createCurrencyMap());
        MvcResult result = this.mockMvc.perform(post("/save/2019-05-22/10/UAH/first")).andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<Purchase> purchases = objectMapper.readValue(contentAsString, typeFactory.constructCollectionType(ArrayList.class, Purchase.class));

        assertEquals(TestData.createPurchases().get(0), purchases.get(0));
        purchaseRepository.deleteAll();
    }


    @Test
    public void givenURIWithValidDate_whenClearByDate_returnDeleteRecordsWire() throws Exception {
        when(ratesMapper.getData()).thenReturn(TestData.createCurrencyMap());
        List<Purchase> purchasesListWithTheSameDate = TestData.createPurchasesListWithTheSameDate();
        for (Purchase purchase : purchasesListWithTheSameDate) {
            purchaseRepository.save(purchase);
        }
        this.mockMvc.perform(delete("/clear/2018-08-29")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("All following records have been successfully deleted "))
                .andExpect(jsonPath("$.purchases").isArray());
        purchaseRepository.deleteAll();
    }

    @Test
    public void givenURIWithYearLessThenAvailable_whenReport_thenThrowValidationException() throws Exception {
        this.mockMvc.perform(get("/report/1290/UAH")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.name").value("report.year: min value 2000"));
    }

    @Test
    public void givenURIWithParamAll_whenAll_thenReturnPaginationWireObject() throws Exception {
        purchaseRepository.deleteAll();
        List<Purchase> purchases = TestData.createPurchasesWithoutId();
        for (Purchase purchase : purchases) {
            purchaseRepository.save(purchase);
        }
        this.mockMvc.perform(get("/all")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.next").value("http://localhost:8080/all?page=1"))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result").isNotEmpty());
        purchaseRepository.deleteAll();


    }
}