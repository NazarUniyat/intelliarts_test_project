package com.nazar.uniyat.intelliarts_test_project.service;

import com.nazar.uniyat.intelliarts_test_project.components.RatesMapper;
import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.exceptions.ConnectionExternalApiException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.WrongDateInputException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.WrongInputException;
import com.nazar.uniyat.intelliarts_test_project.repository.PurchaseRepository;
import com.nazar.uniyat.intelliarts_test_project.testData.TestData;
import com.nazar.uniyat.intelliarts_test_project.wires.DeleteRecordsWire;
import com.nazar.uniyat.intelliarts_test_project.wires.ExceptionWire;
import com.nazar.uniyat.intelliarts_test_project.wires.ReportWire;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseServiceImplTest {

    @Autowired
    PurchaseService purchaseService;

    @MockBean
    RatesMapper ratesMapper;

    @MockBean
    PurchaseRepository purchaseRepository;

    @Test
    public void givenPurchaseWithUAHCurrencyButMapWithRatesIsEmpty_whenSave_thenReturnConnectionExternalApiException() {
        when(ratesMapper.getData()).thenReturn(null);

        Throwable thrown = catchThrowable(() ->
                purchaseService.save("2019-01-01", 120.0, "UAH", "testName"));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(ConnectionExternalApiException.class)
                .hasMessageContaining("there is no information from 'fixer' API, try again later");

    }

    @Test
    public void givenPurchaseWithNonExistingCurrencyXXX_whenSave_thenThrowWrongInputException() {
        when(ratesMapper.getData()).thenReturn(TestData.createMapWithSelectedCurrency("UAH"));

        Throwable thrown = catchThrowable(() ->
                purchaseService.save("2019-01-01", 120.0, "XXX", "testName"));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(WrongInputException.class)
                .hasMessageContaining("currency is not valid");
    }

    @Test
    public void givenPurchaseWithInvalidDateFormat_whenSave_thenThrowWrongDateInputException() {
        when(ratesMapper.getData()).thenReturn(TestData.createMapWithSelectedCurrency("UAH"));
        Throwable thrown = catchThrowable(() ->
                purchaseService.save("2019-15-01", 120.0, "UAH", "testName"));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(WrongDateInputException.class)
                .hasMessage("you try to input date in wrong format, please use YYYY-MM-DD");

    }

    @Test
    public void givenValidPurchase_whenSave_thenReturnListOfPurchasesThatWereMadeOnTheSpecifiedDate() {
        when(ratesMapper.getData()).thenReturn(TestData.createMapWithSelectedCurrency("PLN"));
        when(purchaseRepository.findAllByDate("2018-08-29")).thenReturn(TestData.createPurchasesListWithTheSameDate());
        List<Purchase> savedPurchases = purchaseService.save("2018-08-29", 100.0, "PLN", "bbb");

        assertEquals(savedPurchases.size(), purchaseRepository.findAllByDate("2018-08-29").size());
    }


    @Test
    public void givenDateInInvalidFormat_whenDeleteAllByDate_thenTrowWrongDateInputException() {

        Throwable thrown = catchThrowable(() ->
                purchaseService.deleteAllByDate("2019-11-45"));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(WrongDateInputException.class)
                .hasMessage("you try to input date in wrong format, please use YYYY-MM-DD");
    }

    @Test
    public void givenValidInputDateButNoRecordsForTheSelectedDate_whenDeleteAllByDate_thenThrowWrongInputException() {
        List<Purchase> purchases = new ArrayList<>();
        when(purchaseRepository.findAllByDate("2018-08-29")).thenReturn(purchases);

        Throwable thrown = catchThrowable(() ->
                purchaseService.deleteAllByDate("2018-08-29"));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(WrongInputException.class)
                .hasMessageContaining("No records for deletion for the selected date: 2018-08-29");
    }

    @Test
    public void givenValidInput_whenDeleteAllByDate_thenReturnDeleteRecordWireThatContainListOfDeletedObjects() {
        when(purchaseRepository.findAllByDate("2018-08-29")).thenReturn(TestData.createPurchasesListWithTheSameDate());

        DeleteRecordsWire deleteRecordsWire = purchaseService.deleteAllByDate("2018-08-29");

        assertEquals(deleteRecordsWire.getMessage(), "All following records have been successfully deleted ");
        assertEquals(deleteRecordsWire.getPurchases(), TestData.createPurchasesListWithTheSameDate());
    }

    @Test
    public void givenValidInputButThereIsNoRecordInGivenYear_whenReport_thenReturnReportWireWithMessageThatThereIsNoInfo() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        when(purchaseRepository.purchaseByYear(2011)).thenReturn(purchases);

        ReportWire reportInUah = purchaseService.report("UAH", 2011);

        assertEquals(reportInUah.getMessage(), "there is no report for the selected year");
        assertEquals(reportInUah.getCurrency(), "No currency");
        assertEquals(reportInUah.getIncome(), new Double(0.0));
        assertEquals(reportInUah.getReportYear(), new Integer(2011));
    }

    @Test
    public void givenValidInputButNoInformationAboutRates_whenReport_thenThrowConnectionExternalApiException() {
        when(purchaseRepository.purchaseByYear(2018)).thenReturn(TestData.createPurchasesListWithTheSameDate());
        when(ratesMapper.getData()).thenReturn(null);

        Throwable thrown = catchThrowable(() ->
                purchaseService.report("UAH", 2018));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(ConnectionExternalApiException.class)
                .hasMessageContaining("report is unavailable now. No information from 'fixer' API");
    }


    @Test
    public void givenInvalidInputWithNonExistingCurrency_whenReport_thenThrowWrongInputException() {
        when(purchaseRepository.purchaseByYear(2018)).thenReturn(TestData.createPurchasesListWithTheSameDate());
        when(ratesMapper.getData()).thenReturn(TestData.createCurrencyMap());

        Throwable thrown = catchThrowable(() ->
                purchaseService.report("XXX", 2018));

        Assertions.assertThat(thrown)
                .isNotNull()
                .isInstanceOf(WrongInputException.class)
                .hasMessageContaining("currency is not valid");

    }

    @Test
    public void givenValidInput_whenReport_thenReturnReportWire() {
        when(purchaseRepository.purchaseByYear(2018)).thenReturn(TestData.createPurchasesListWithTheSameDate());
        when(ratesMapper.getData()).thenReturn(TestData.createCurrencyMap());

        ReportWire uah = purchaseService.report("UAH", 2018);
        assertEquals(uah.getReportYear(), new Integer(2018));
        assertEquals(uah.getIncome(), new Double(3702.38));
        assertEquals(uah.getCurrency(), "UAH");
        assertEquals(uah.getMessage(), "report for 2018");

    }
}