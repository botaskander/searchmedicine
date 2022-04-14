package com.searchmedicine.demo;

import com.searchmedicine.demo.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SearchMedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchMedicineApplication.class, args);
    }

}
