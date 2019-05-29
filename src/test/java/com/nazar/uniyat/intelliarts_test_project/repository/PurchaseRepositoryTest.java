package com.nazar.uniyat.intelliarts_test_project.repository;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.testData.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PurchaseRepositoryTest {

    @Autowired
    PurchaseRepository purchaseRepository;

    @Test
    public void givenOneDayAtDateFormat_whenFindAllByDate_thenReturnRecordsThatWereStoredByGivenDate() {
        List<Purchase> purchases = TestData.createPurchases();
        for (Purchase purchase : purchases) {
            purchaseRepository.save(purchase);
        }
        List<Purchase> allByDate = purchaseRepository.findAllByDate("2018-08-29");
        assertEquals(allByDate.size(), 2);
        assertEquals(allByDate.get(0), purchases.get(3));
        purchaseRepository.deleteAll();
    }

    @Test
    public void givenOneDayInDateFormat_whenDeletePurchasesByDateIs_thenReturnEmptyList() {
        List<Purchase> purchases = TestData.createPurchases();
        purchaseRepository.save(purchases.get(3));
        purchaseRepository.save(purchases.get(4));
        List<Purchase> allByDate = purchaseRepository.findAllByDate("2018-08-29");
        System.out.println(allByDate);
        assertEquals(allByDate.size(), 2);
        purchaseRepository.deletePurchasesByDateIs("2018-08-29");
        assertEquals(purchaseRepository.findAllByDate("2018-08-29").size(), 0);
        purchaseRepository.deleteAll();
    }

    @Test
    public void givenYear_whenPurchaseByYear_thenReturnListThatContainsPurchasesThatWereMadeInGivenYear() {
        List<Purchase> purchases = TestData.createPurchases();
        for (Purchase purchase : purchases) {
            purchaseRepository.save(purchase);
        }
        assertEquals(purchaseRepository.purchaseByYear(2019).size(),3);
        purchaseRepository.deleteAll();
    }


}