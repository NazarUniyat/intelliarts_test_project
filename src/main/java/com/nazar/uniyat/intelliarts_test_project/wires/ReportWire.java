package com.nazar.uniyat.intelliarts_test_project.wires;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportWire {

    private LocalDate today;
    private String message;
    private Double income;
    private Integer reportYear;
    private String currency;

}
