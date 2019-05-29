package com.nazar.uniyat.intelliarts_test_project.service;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.wires.DeleteRecordsWire;
import com.nazar.uniyat.intelliarts_test_project.wires.PaginationWire;
import com.nazar.uniyat.intelliarts_test_project.wires.ReportWire;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PurchaseService {

    List<Purchase> save(String date, Double price, String currency, String name);

    PaginationWire findAllWithPagination(Pageable pageable);

    DeleteRecordsWire deleteAllByDate(String date);

    ReportWire report(String currency, Integer year);


}
