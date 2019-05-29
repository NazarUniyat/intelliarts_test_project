package com.nazar.uniyat.intelliarts_test_project.controllers;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.service.PurchaseService;
import com.nazar.uniyat.intelliarts_test_project.wires.DeleteRecordsWire;
import com.nazar.uniyat.intelliarts_test_project.wires.PaginationWire;
import com.nazar.uniyat.intelliarts_test_project.wires.ReportWire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@Validated
public class PurchaseController {


    private PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping("/save/{date}/{price}/{currency}/{name}")
    @ResponseBody
    public ResponseEntity<List<Purchase>> save(
            @PathVariable @Size(min = 10, max = 10, message = "wrong count of characters in date format") String date,
            @PathVariable @Min(value = 0, message = "only positive numbers!!!") Double price,
            @PathVariable @Size(min = 3, max = 3, message = "wrong input, more or less then 3 characters") String currency,
            @PathVariable @Size(min = 2, max = 50, message = "name of goods should be more than 2 characters and less then 50") String name
    ) {
        List<Purchase> purchases = purchaseService.save(date, price, currency, name);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(purchases);
    }

    @DeleteMapping("clear/{date}")
    @ResponseBody
    public ResponseEntity<DeleteRecordsWire> clearByDate(
            @PathVariable String date
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.deleteAllByDate(date));
    }

    @GetMapping("/report/{year}/{currency}")
    @ResponseBody
    public ResponseEntity<ReportWire> report(
            @PathVariable @Min(value = 2000, message = "min value 2000") Integer year,
            @PathVariable String currency
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.report(currency, year));
    }


    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<PaginationWire> all(
            @PageableDefault(value = 5) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.findAllWithPagination(pageable));
    }


}
