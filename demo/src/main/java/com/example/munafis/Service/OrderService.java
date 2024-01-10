package com.example.munafis.Service;

import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.OrderDTO;
import com.example.munafis.Model.*;
import com.example.munafis.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final CompanyRepository companyRepository;
    private final ProductsDetailsRepository productsDetailsRepository;
    private final AuthRepository authRepository;
    private final ProviderRepository providerRepository;


    //Admin
    public List getAllOrders() {
        return orderRepository.findAll();
    }


    public void addOrder(OrderDTO orderDTO,Integer user_id) {
        double totalPriceProduct = 0;
        double totalPriceService = 0;
        double totalPrice = 0;
        double price = 0;

        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(orderDTO.getCompany_id());

        if (!company.getId().equals(user.getCompany().getId())){
            throw new ApiException("Invalid input, you cannot add an order for this company");
        }

        Set<ProductsDetails> productsDetails = new HashSet<>();
        Set<com.example.munafis.Model.Service> serviceList = new HashSet<>();

        for (ProductsDetails productsDetails1 : orderDTO.getProductsDetails()) {
            Product product = productRepository.findProductById(productsDetails1.getId());
            if (product == null) {
                throw new ApiException("product id not found");
            }
            if(product.getStock() < productsDetails1.getQuantity()){
                throw new ApiException("product is not available now");
            }
            product.setStock(product.getStock()-productsDetails1.getQuantity());

            price += product.getPrice() * productsDetails1.getQuantity();

            ProductsDetails productsDetails2 = new ProductsDetails(null, productsDetails1.getQuantity(), null, null);
            totalPriceProduct += price;
            productsDetails.add(productsDetails2);
            productsDetailsRepository.saveAll(productsDetails);
            productsDetailsRepository.save(productsDetails1);
            productsDetailsRepository.save(productsDetails2);
        }

        for (com.example.munafis.Model.Service s : orderDTO.getServices()) {

            com.example.munafis.Model.Service service = serviceRepository.findServiceById(s.getId());
            if (service == null) {
                throw new ApiException("service not found");
            }else {
                totalPriceService += s.getPrice();
                serviceList.add(s);
            }
        }
        totalPrice = totalPriceProduct + totalPriceService;
        Orderr orderr = new Orderr(null, "pending", totalPrice, serviceList, productsDetails, company);
        company.getOrders().add(orderr);
        companyRepository.save(company);



        for (ProductsDetails productsDetails1: orderr.getProductsDetails()){
            productsDetails1.setOrder(orderr);
            productsDetailsRepository.save(productsDetails1);
        }

        for(com.example.munafis.Model.Service service: serviceList){
            service.setOrder(orderr);
            serviceRepository.save(service);
        }

        orderRepository.save(orderr);

    }

    public void updateOrder(OrderDTO orderDTO , Integer user_id,Integer order_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Orderr order = orderRepository.findOrderrById(order_id);
        Orderr orderr1 = new Orderr();

        for (Orderr orderr:company.getOrders()){
            if (orderr.getId().equals(order.getId())){
                orderr1 = orderr;
                break;
            }
        }
        if(order==null){
            throw new ApiException("order not found");
        }
        if(!order.getStatus().equals("pending") && !order.getStatus().equals("completed")){
            throw new ApiException("the order cannot be updated");
        }else if (!orderr1.getCompany().getId().equals(company.getId())){
            throw new ApiException("invalid input you cannot update offer");
        }

        order.setServices(orderDTO.getServices());
        order.setProductsDetails(orderDTO.getProductsDetails());
        orderRepository.save(order);
    }


    public void deleteOrder(Integer id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Orderr order = orderRepository.findOrderrById(id);
        for (Orderr orderr:company.getOrders()){
            if (orderr.getId().equals(order.getId())){
                company.getOrders().remove(orderr);
                orderRepository.delete(orderr);
                companyRepository.save(company);
                break;
            }

        }
        if(order==null){
            throw new ApiException("order not found");
        }
        if(!order.getStatus().equals("pending")){
            throw new ApiException("the order cannot deleted");
        }

    }

    public String invoice(Integer order_id,Integer user_id){
        User user = authRepository.findUserById(user_id);
        Company company = companyRepository.findCompanyById(user.getCompany().getId());
        Orderr order = orderRepository.findOrderrById(order_id);
        Orderr orderr1 = new Orderr();
        for (Orderr orderr:company.getOrders()){
            if (orderr.getId().equals(order.getId())){
                orderr1 = orderr;

            }
        }

        if(order==null){
            throw new ApiException("order not found");
        }

        if (!orderr1.getCompany().getId().equals(company.getId())){
            throw new ApiException("You cannot issue invoice for this order");
        }
        if(!order.getStatus().equals("accepted")){
            throw new ApiException("invoice cannot be issued before the order is accepted");
        }
        List<ProductsDetails> productsDetails = new ArrayList<>(order.getProductsDetails());
        List<com.example.munafis.Model.Service> serviceList = new ArrayList<>(order.getServices());
        for (com.example.munafis.Model.Service service : order.getServices()){
            serviceList.add(service);
        }

        for (ProductsDetails p : order.getProductsDetails()){
            productsDetails.add(p);
        }

        return "invoice details " + '\n' +
                "Company Name: " + order.getCompany().getCompanyName()  + '\n' +
                "Total Price: " + order.getTotalPrice() +   '\n' +
                "Services: " + serviceList  +   '\n' +
                 "Products: " + productsDetails + '\n';

    }

    public void acceptOrder(Integer user_id, Integer order_id){
        User user = authRepository.findUserById(user_id);
        Orderr order = orderRepository.findOrderrById(order_id);
        if(user==null){
            throw new ApiException("user not found");
        }
        if(order==null){
            throw new ApiException("order not found");
        }
        if(!user.getRole().equals("Admin")){
            throw new ApiException("not authorised");
        }
        else {
            order.setStatus("accepted");
            orderRepository.save(order);
        }
    }


}

