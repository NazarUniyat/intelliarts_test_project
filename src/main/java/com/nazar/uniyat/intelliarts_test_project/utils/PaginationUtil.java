package com.nazar.uniyat.intelliarts_test_project.utils;

import com.nazar.uniyat.intelliarts_test_project.wires.PaginationWire;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationUtil {

    public static PaginationWire getPaginationResult(List resultFromDb, Pageable pageable, String pageURL) {
        PaginationWire paginationWire = new PaginationWire();
        paginationWire.setNext(pageURL + pageable.next().getPageNumber());
        paginationWire.setResult(resultFromDb);
        if (resultFromDb.size() < pageable.next().getPageSize()) {
            paginationWire.setNext(null);
            return paginationWire;
        }
        return paginationWire;

    }
}
