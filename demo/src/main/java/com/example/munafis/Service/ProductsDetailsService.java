package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.ProductDetalisDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.ProductsDetails;
import com.example.munafis.Model.Provider;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.AuthRepository;
import com.example.munafis.Repository.ProductRepository;
import com.example.munafis.Repository.ProductsDetailsRepository;
import com.example.munafis.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductsDetailsService {

    private final ProductsDetailsRepository productsDetailsRepository;
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final AuthRepository authRepository;




     //Only provider
    public void addProductsDetails(ProductDetalisDTO productDetalisDTO){
        Product product= productRepository.findProductById(productDetalisDTO.getProduct_id());

        if(product==null){
            throw new ApiException("Product id not found");
        }
        ProductsDetails productsDetails = new ProductsDetails(null,productDetalisDTO.getQuantity(),null,null);
        productsDetailsRepository.save(productsDetails);
    }


    public void updateProductsDetails(Integer id,ProductDetalisDTO productDetalisDTO){

        Product product = productRepository.findProductById(productDetalisDTO.getProduct_id());
        if(product==null){
            throw new ApiException("Product id not found");
        }
        ProductsDetails oldproductsDetails= productsDetailsRepository.findProductsDetailsById(id);

        if(oldproductsDetails==null){
            throw new ApiException("product details id not found");
        }
        oldproductsDetails.setQuantity(productDetalisDTO.getQuantity());
        productsDetailsRepository.save(oldproductsDetails);

    }



    public void addStock(Integer product_id ,Integer quantity,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Provider provider= providerRepository.findProviderById(user.getProvider().getId());
        Product product = productRepository.findProductById(product_id);
        Product p2 = new Product();
        for (Product product1:provider.getProducts()){
            if (product1.getId().equals(product.getId())){
                p2 = product1;
            }
        }
        if(product==null){
            throw new ApiException("product id not found");
        }
        if (!p2.getProvider().getId().equals(product.getId())){
            throw new ApiException("invalid, you cannot add stock to this product");
        }

        product.setStock(product.getStock()+quantity);
        productRepository.save(product);
    }


}
