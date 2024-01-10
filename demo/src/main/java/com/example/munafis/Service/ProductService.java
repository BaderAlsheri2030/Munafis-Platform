package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.ProductDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.Provider;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.AuthRepository;
import com.example.munafis.Repository.ProductRepository;
import com.example.munafis.Repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final AuthRepository authRepository;


    //ALL
    public List getAllProducts(){

        return productRepository.findAll();
    }



    public List getMyProducts(Integer user_id){
        User user=authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        List<Product> products = productRepository.findAllByProviderId(provider.getId());
        if(!provider.getId().equals(user_id)){
            throw new ApiException("unauthorised");
        }
        if (products.isEmpty()){
            throw new ApiException("products not found");
        }
        return products;
    }


    //Only provider
    public void addProduct(ProductDTO productDTO, Integer user_id){
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        if(provider==null){
            throw new ApiException("provider Id not found");
        }else if (provider.getId().equals(productDTO.getProvider_id())){
            throw new ApiException("Invalid provider input");
        }

        Product product = new Product(null,productDTO.getName(),productDTO.getPrice(),productDTO.getStock(),provider,null);
        productRepository.save(product);
    }




    //Only provider
    public void updateProduct(Integer id,ProductDTO productDTO,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        Product oldProduct = productRepository.findProductById(id);
        Product product2 = new Product();
        for (Product product:provider.getProducts()){
            if (product.getId().equals(oldProduct.getId())){
                product2 = oldProduct;
                break;
            }
        }
        if(oldProduct==null){
            throw new ApiException("Product Id not found");
        }else if (!product2.getProvider().getId().equals(provider.getId())){
            throw new ApiException("invalid product input, you cannot update this product");
        }

        oldProduct.setName(productDTO.getName());
        oldProduct.setPrice(productDTO.getPrice());
        productRepository.save(oldProduct);
    }




    //Only provider
    public void deleteProduct(Integer id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        Product product=productRepository.findProductById(id);
        if(product==null){
            throw new ApiException("product id  not found");
        }else if (product.getProvider().getId().equals(provider.getId())){
            throw new ApiException("Invalid, you cannot delete this product");
        }
        productRepository.delete(product);
    }



    //ALL
    public List getProductsByName(String name){
        List<Product> products =productRepository.findProductsByName(name);
        if(products.isEmpty()){
            throw new ApiException("no products same this name");
        }
        return products;
    }



    //All
    public Product displayProductInfo(Integer id){
        Product product=productRepository.findProductById(id);
        if(product==null){
            throw new ApiException("product id not found");
        }
        return product;
    }
    public List<Product> getAllProductsByProvider(String name){
        List<Product> products = productRepository.findAllByProviderCompanyName(name);
        return products;
    }

    //All
    public List getAllByOrderByPrice(){
        List<Product> products =productRepository.findAllByOrderByPrice();
        if(products==null){
            throw new ApiException("no products");
        }
        return products;
    }





}
