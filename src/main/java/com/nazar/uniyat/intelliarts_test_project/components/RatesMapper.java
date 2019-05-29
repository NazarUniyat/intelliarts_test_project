package com.nazar.uniyat.intelliarts_test_project.components;

import com.nazar.uniyat.intelliarts_test_project.service.FixerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@Log4j2
public class RatesMapper {

    private FixerService fixerService;
    private HashMap<String, Double> data;


    @Autowired
    public void setFixerService(FixerService fixerService) {
        this.fixerService = fixerService;
    }

    public void setData(HashMap<String, Double> data) {
        this.data = data;
    }

    @Scheduled(fixedDelay = 1000000)
    private void mappedRates() {
        try {
            this.data = fixerService.getRatesMap();
        } catch (NullPointerException e) {
            log.error("map is empty!");
        }
    }

    public Map<String, Double> getData() {
        return data;
    }
}
