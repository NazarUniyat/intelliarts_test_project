package com.nazar.uniyat.intelliarts_test_project.wires;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRecordsWire {

    String message;
    List<Purchase> purchases;
}
