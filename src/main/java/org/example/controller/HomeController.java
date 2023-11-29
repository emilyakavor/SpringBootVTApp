package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.entity.Product;
import org.example.repo.ProductRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/product")
    public String saveProduct() throws InterruptedException {

        for(int i=0; i< 10; i++) {
            String randomString = RandomStringUtils.randomAlphanumeric(5);
            log.info("Start of ThreadID=" + randomString + " | " + Thread.currentThread());

            Product product = new Product();
            product.setProductName(randomString);
            product.setPrice(RandomUtils.nextLong(10, 1000));
            product.setPrice(1L);
            productRepository.save(product);

            // Let's block for 10 secs,
            // to see if truly and luckily,
            // our VT will be pinned to another platform thread
            Thread.sleep(10000);
            log.info("End of ThreadID=" + randomString + " | " + Thread.currentThread());
        }

        return "Ops successful";
    }
}