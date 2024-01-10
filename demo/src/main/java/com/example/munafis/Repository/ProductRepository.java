package com.example.munafis.Repository;


import com.example.munafis.Model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


    Product findProductById(Integer id);

    List<Product>  findProductsByName(String name);
    List<Product> findAllByOrderByPrice();
    List<Product> findAllByProviderId(Integer id);
    List<Product> findAllByProviderCompanyName(String name);



}
