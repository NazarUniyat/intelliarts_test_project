package com.nazar.uniyat.intelliarts_test_project.repository;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findAllByDate(String date);

    @Query("from Purchase order by date desc ")
    List<Purchase> findAllWithPagination(Pageable pageable);

    void deletePurchasesByDateIs(String date);

    @Query(value = "select * from Purchase  where date like ?1%", nativeQuery = true)
    List<Purchase> purchaseByYear(Integer year);


//    @Query(value = "select date,sum(price),currency from (select * from shop.purchase where YEAR(date)=:ye) as R group by R.currency;", nativeQuery = true)
//    List<ReportWire> report(@Param("ye") Integer year);

}
