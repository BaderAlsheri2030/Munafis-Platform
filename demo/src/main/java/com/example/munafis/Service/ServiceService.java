package com.example.munafis.Service;


import com.example.munafis.API.ApiException;
import com.example.munafis.DTO.ProductDTO;
import com.example.munafis.DTO.ServiceDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.Provider;
import  com.example.munafis.Model.Service;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.AuthRepository;
import com.example.munafis.Repository.ProviderRepository;
import com.example.munafis.Repository.ServiceRepository;
import lombok.RequiredArgsConstructor;


import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {


    private final ServiceRepository serviceRepository;
    private final ProviderRepository providerRepository;
    private final AuthRepository authRepository;


    //All
    public List getAllServices() {
        return serviceRepository.findAll();
    }

    //All
    public List getAllByOrderByPrice(){
        List<Service> services =serviceRepository.findAllByOrderByPrice();
        if(services==null){
            throw new ApiException("no services");
        }
        return services;
    }
    //all
    public List getServicesByName(String name){
        List<Service> services =serviceRepository.findServicesByServiceName(name);
        if(services.isEmpty()){
            throw new ApiException("no services same this name");
        }
        return services;
    }
    public List getMyServices(Integer user_id){
        User user=authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        List<Service> services = serviceRepository.findAllByProviderId(provider.getId());
        if(!provider.getId().equals(user_id)){
            throw new ApiException("cannot see this services");
        }
        if (services.isEmpty()){
            throw new ApiException("services not found");
        }
        return services;
    }
    public void addService(ServiceDTO serviceDTO, Integer user_id){
        User user =authRepository .findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        if(provider==null){
            throw new ApiException("provider Id not found");
        }else if (provider.getId().equals(serviceDTO.getProvider_id())){
            throw new ApiException("Invalid provider input");
        }

        Service service = new Service(null,serviceDTO.getServiceName(),serviceDTO.getServiceType(),serviceDTO.getServiceDetails(),serviceDTO.getPrice(),provider,null);
        serviceRepository.save(service);
    }
    public void updateService(Integer id, ServiceDTO serviceDTO ,Integer user_id) {
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());

        Service oldService = serviceRepository.findServiceById(id);
        Service service2 = new Service();
        for (Service service:provider.getServices()){
            if(service.getId().equals(oldService.getId())){
                service2 =oldService;
            }
        }
        if (oldService == null) {
            throw new ApiException("Service Id not found");
        }else if(!service2.getProvider().getId().equals(provider.getId())){
            throw new ApiException("invalid Services input, you cannot update this Service");

        }

        oldService.setServiceName(serviceDTO.getServiceName());
        oldService.setServiceDetails(serviceDTO.getServiceDetails());
        oldService.setServiceType(serviceDTO.getServiceType());
        oldService.setPrice(serviceDTO.getPrice());
        serviceRepository.save(oldService);

    }
    public void deleteService(Integer id, Integer user_id) {
        User user = authRepository.findUserById(user_id);
        Provider provider = providerRepository.findProviderById(user.getProvider().getId());
        Service service = serviceRepository.findServiceById(id);

        if (service == null) {
            throw new ApiException("service Id not found");
        }else if(!service.getProvider().getId().equals(user_id)){
            throw new ApiException("Invalid, you cannot delete this service");
        }
        serviceRepository.delete(service);
    }
}



