package com.nazar.uniyat.intelliarts_test_project.service;

import com.nazar.uniyat.intelliarts_test_project.components.RatesMapper;
import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.exceptions.ConnectionExternalApiException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.QuarryingDatabaseException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.WrongDateInputException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.WrongInputException;
import com.nazar.uniyat.intelliarts_test_project.repository.PurchaseRepository;
import com.nazar.uniyat.intelliarts_test_project.utils.ConverterUtil;
import com.nazar.uniyat.intelliarts_test_project.utils.PaginationUtil;
import com.nazar.uniyat.intelliarts_test_project.wires.DeleteRecordsWire;
import com.nazar.uniyat.intelliarts_test_project.wires.PaginationWire;
import com.nazar.uniyat.intelliarts_test_project.wires.ReportWire;
import lombok.extern.log4j.Log4j2;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class PurchaseServiceImpl implements PurchaseService {


    @Value("${pagination.link}")
    private String pageURl;
    private PurchaseRepository purchaseRepository;
    private RatesMapper ratesMapper;


    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, RatesMapper ratesMapper) {
        this.purchaseRepository = purchaseRepository;
        this.ratesMapper = ratesMapper;
    }

    @Override
    public List<Purchase> save(String date, Double price, String currency, String name) {

        try {
            ratesMapper.getData().containsKey(currency);
        } catch (NullPointerException e) {
            throw new ConnectionExternalApiException("there is no information from 'fixer' API, try again later");
        }

        if (!(ratesMapper.getData().containsKey(currency))) {
            throw new WrongInputException("currency is not valid");
        }

        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);
        } catch (DateTimeParseException | ConstraintViolationException e) {
            throw new WrongDateInputException();
        }
        purchaseRepository.save(new Purchase(parsedDate.toString(), price, currency, name));
        return purchaseRepository.findAllByDate(parsedDate.toString());
    }

    @Override
    public PaginationWire findAllWithPagination(Pageable pageable) {
        List<Purchase> allWithPagination = purchaseRepository.findAllWithPagination(pageable);
        return PaginationUtil.getPaginationResult(allWithPagination, pageable, this.pageURl);
    }


    @Override
    @Transactional
    public DeleteRecordsWire deleteAllByDate(String date) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date);

        } catch (DateTimeParseException | ConstraintViolationException e) {
            throw new WrongDateInputException();
        }

        List<Purchase> allByDate = purchaseRepository.findAllByDate(parsedDate.toString());

        if (allByDate.isEmpty()) {
            throw new WrongInputException("No records for deletion for the selected date: " + date);
        }

        try {
            purchaseRepository.deletePurchasesByDateIs(parsedDate.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuarryingDatabaseException("delete failed");
        }

        return new DeleteRecordsWire("All following records have been successfully deleted ", allByDate);
    }

    @Override
    public ReportWire report(String currency, Integer year) {
        List<Purchase> all = purchaseRepository.purchaseByYear(year);
        Map<String, Double> data = ratesMapper.getData();

        if (all.isEmpty()) {
            return new ReportWire(LocalDate.now(), "there is no report for the selected year", 0.0, year, "No currency");
        }
        try{
            data.get(currency);
        }catch (NullPointerException e){
            throw new ConnectionExternalApiException("report is unavailable now. No information from 'fixer' API");
        }
        double sumInEUR = all.stream().mapToDouble(element -> ConverterUtil.convertToEUR(ratesMapper, element.getCurrency(), element.getPrice())).sum();

        Double currencyToReport = data.get(currency);
        if (currencyToReport == null) {
            throw new WrongInputException("currency is not valid");
        }
        double report = sumInEUR * currencyToReport;
        return new ReportWire(LocalDate.now(), "report for " + year.toString(), DoubleRounder.round(report, 2), year, currency);
    }
}

