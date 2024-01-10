package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.CompanyDTO;
import com.example.munafis.DTO.ProviderDTO;
import com.example.munafis.Model.*;
import com.example.munafis.Model.Provider;
import com.example.munafis.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OffersRepository offersRepository;
    private final CompanyRepository companyRepository;
    private final RfpRepository rfpRepository;
    private final AuthRepository authRepository;

    public List<Provider> getAllProviders(Integer user_id)
    {   User user =authRepository.findUserById(user_id);
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("You have no access");
        }
        return providerRepository.findAll();
    }


    //Register
    public void register(ProviderDTO providerDTO ){
        String hash = new BCryptPasswordEncoder().encode(providerDTO.getPassword());
        providerDTO.setPassword(hash);
        User user = new User(null,providerDTO.getUsername(),providerDTO.getPassword(),providerDTO.getEmail(),providerDTO.getRole(),null,null);


        user.setRole("PROVIDER");

        Provider provider = new Provider(null, providerDTO.getCompanyName(), providerDTO.getBusinessNumber(), providerDTO.getAddress(), providerDTO.getField(), user,null,null,null);
        user.setProvider(provider);
        authRepository.save(user);
        providerRepository.save(provider);

    }


    public void updateProvider(Integer user_id, Provider provider) {
        User user = authRepository.findUserById(user_id);
        Provider oldProvider = providerRepository.findProviderById(user.getProvider().getId());
        if (oldProvider == null) {
            throw new ApiException("Provider id not found");
        }else if (!oldProvider.getId().equals(user.getProvider().getId())){
            throw new ApiException("invalid");
        }

        oldProvider.setAddress(provider.getAddress());
        oldProvider.setField(provider.getField());
        oldProvider.setBusinessNumber(provider.getBusinessNumber());
        oldProvider.setCompanyName(provider.getCompanyName());


        providerRepository.save(oldProvider);

    }


    public void deleteProvider(Integer id) {
        Provider provider = providerRepository.findProviderById(id);

        if (provider == null) {
            throw new ApiException("Provider id not found");
        }
        providerRepository.delete(provider);
    }

    //ALL
    public Set<Product> getAllProductsByProvider(String providerName) {
        Provider provider = providerRepository.findByCompanyName(providerName);
        if (provider == null) {
            throw new ApiException("company name not available");
        }
        if (provider.getProducts().isEmpty()) {
            throw new ApiException("this provider not have products");
        }
        return provider.getProducts();
    }


    //ALL
    public Set<com.example.munafis.Model.Service> getAllServicesByProvider(String providerName) {
        Provider provider = providerRepository.findByCompanyName(providerName);
        if (provider == null) {
            throw new ApiException("company name not available");
        }
        if (provider.getServices().isEmpty()) {
            throw new ApiException("this provider not have Services");
        }
        return provider.getServices();
    }

    public List<Offer> viewMyAcceptedOffers(Integer user_id) {
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        if (provider == null) {
            throw new ApiException("invalid provider");
        }
        List<Offer> acceptedOffers = offersRepository.findAllByProviderIdAndStatusEquals(provider.getId(), "accepted");
        if (acceptedOffers.isEmpty()) {
            throw new ApiException("sorry, there is no accepted offers now");
        }else if (!provider.getUser().getId().equals(user_id)){
            throw new ApiException("invalid ");
        }
        return acceptedOffers;
    }

    public List<Offer> viewMyPendingOffers(Integer user_id) {
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        if (provider == null) {
            throw new ApiException("invalid provider");
        }
        List<Offer> pendingOffers = offersRepository.findAllByProviderIdAndStatusEquals(provider.getId(), "pending");
        if (pendingOffers.isEmpty()) {
            throw new ApiException("sorry, there is no pending offers now");
        }else if (!provider.getUser().getId().equals(user_id)){
        throw new ApiException("invalid ");
    }
        return pendingOffers;
    }

    public List<Offer> viewMyRejectedOffers(Integer user_id) {
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        if (provider == null) {
            throw new ApiException("invalid provider");
        }
        List<Offer> rejectedOffers = offersRepository.findAllByProviderIdAndStatusEquals(provider.getId(), "rejected");
        if (rejectedOffers.isEmpty()) {
            throw new ApiException("there is no rejected");
        }else if (!provider.getUser().getId().equals(user_id)){
            throw new ApiException("invalid ");
        }
        return rejectedOffers;
    }

}



