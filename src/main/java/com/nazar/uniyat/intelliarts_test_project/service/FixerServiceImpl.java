package com.nazar.uniyat.intelliarts_test_project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nazar.uniyat.intelliarts_test_project.exceptions.ConnectionExternalApiException;
import com.nazar.uniyat.intelliarts_test_project.wires.FixerResponseWire;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;


@Service
@Log4j2
public class FixerServiceImpl implements FixerService {

    @Value("${connection.url}")
    private String apiUrl;


    public HashMap<String, Double> getRatesMap() {

        ObjectMapper objectMapper = new ObjectMapper();
        HttpResponse<JsonNode> jsonResponse = null;
        try {
            jsonResponse = Unirest.get(apiUrl).asJson();
        } catch (UnirestException e) {
            log.error("maybe there is no connection with internet or wrong API");
            throw new ConnectionExternalApiException("maybe there is no connection with internet or wrong API");
        }

        FixerResponseWire fixerResponse = null;
        try {
            fixerResponse = objectMapper.readValue(jsonResponse.getBody().toString(), FixerResponseWire.class);
        } catch (IOException e) {
            log.error("impossible to get a mapped response");
            throw new ConnectionExternalApiException("impossible to get a mapped response");
        }
        System.out.println(fixerResponse);


        return fixerResponse.getRates();
    }

}
