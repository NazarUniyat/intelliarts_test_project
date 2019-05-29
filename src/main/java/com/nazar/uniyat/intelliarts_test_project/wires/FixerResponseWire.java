package com.nazar.uniyat.intelliarts_test_project.wires;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
@ToString
public class FixerResponseWire {

    private Date date;
    private boolean success;
    private HashMap<String, Double> rates;
    private Timestamp timestamp;
    private String base;


}
