package com.example.munafis.Repository;

import com.example.munafis.Model.ProductsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductsDetails,Integer> {

    ProductsDetails findProductDetailsById(Integer id);

}
