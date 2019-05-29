package com.nazar.uniyat.intelliarts_test_project;

import com.nazar.uniyat.intelliarts_test_project.domains.Purchase;
import com.nazar.uniyat.intelliarts_test_project.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.ZonedDateTime;


@SpringBootApplication
public class IntelliartsTestProjectApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(IntelliartsTestProjectApplication.class, args);
        PurchaseRepository purchaseRepository = (PurchaseRepository) run.getBean("purchaseRepository");
        purchaseRepository.deleteAll();
        purchaseRepository.save(new Purchase("2019-05-22", 1100, "UAH", "Book"));
        purchaseRepository.save(new Purchase("2019-05-21", 20, "USD", "notebook"));
        purchaseRepository.save(new Purchase("2019-05-29", 10, "USD", "bottle"));
        purchaseRepository.save(new Purchase("2018-08-29", 300, "USD", "Phone"));
        purchaseRepository.save(new Purchase("2018-08-29", 3000, "PLN", "good1"));
        purchaseRepository.save(new Purchase("2018-09-09", 3000, "PLN", "good2"));
        purchaseRepository.save(new Purchase("2019-02-03", 3000, "PLN", "good3"));
        purchaseRepository.save(new Purchase("2019-03-03", 30000, "PLN", "laptop"));
        purchaseRepository.save(new Purchase("2017-03-08", 800, "PLN", "S8"));
        purchaseRepository.save(new Purchase("2017-04-08", 100, "UAH", "Paper"));

    }

}
