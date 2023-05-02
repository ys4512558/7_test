package com.springboot.test.data.dao.impl;

import com.springboot.test.data.dao.ProductDAO;
import com.springboot.test.data.entity.Product;
import com.springboot.test.data.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductDAOImpl.class);
    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product insertProduct(Product product) {
        LOGGER.info("product createat : " + product.getCreatedAt());
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product selectProduct(Long number) {
        //getById 사장됨 >> 따라서 getReferenceById사용
        //Product selectedProduct = productRepository.getById(number);
        Product selectedProduct = productRepository.getReferenceById(number);

        return selectedProduct;
    }

    @Override
    public Product updateProductName(Long number, String name) throws Exception {

        Optional<Product> selectedProduct = productRepository.findById(number);

        Product updatedProduct = selectedProduct.get();

        if(selectedProduct.isPresent()){
            Product product = selectedProduct.get();

            product.setName(name);
            product.setUpdatedAt(LocalDateTime.now());

            updatedProduct = productRepository.save(product);
        }
        else{
            throw new Exception();
        }

        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {

        Optional<Product> selectedProduct = productRepository.findById(number);

        if(selectedProduct.isPresent()){
            Product product = selectedProduct.get();

            productRepository.delete(product);
        }
        else {
            throw new Exception();
        }

    }
}
