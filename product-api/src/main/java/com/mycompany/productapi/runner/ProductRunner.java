package com.mycompany.productapi.runner;

import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Log4j2
@RequiredArgsConstructor
@Component
public class ProductRunner implements CommandLineRunner {

    private static final Random random = new Random();

    @Value("${runner.num-products}")
    private int numProducts;

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        Flux<Product> products = Flux.range(1, numProducts)
                .map(i -> {
                    Product product = new Product();
                    product.setName(String.format("Product-%s", i));
                    product.setPrice(BigDecimal.valueOf(199).multiply(BigDecimal.valueOf(random.nextDouble())).setScale(2, RoundingMode.HALF_EVEN));
                    return product;
                }).flatMap(productRepository::save);

        productRepository.saveAll(products)
                .thenMany(productRepository.findAll())
                .subscribe(log::info);
    }

}
