package com.example.munafis;
import com.example.munafis.Model.*;
import com.example.munafis.Repository.AuthRepository;
import com.example.munafis.Repository.CompanyRepository;
import com.example.munafis.Repository.ProviderRepository;
import com.example.munafis.Service.CompanyService;
import com.example.munafis.Service.ProviderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)

public class ServiceTest {

    @InjectMocks
    ProviderService providerService;

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;
    @Mock
    AuthRepository authRepository;
    @Mock
    ProviderRepository providerRepository;


    User user;

    Company company;
    Provider provider1,provider2;

    Service service1,service2;

    @BeforeEach
    void setUp(){
        user = new User(null,"nx22","1234", "n@gmail.com","Provider", null,null);
        company = new Company(null,"Urban", "123","COMPANY1@gmail.com" ,null,null,null);
        provider1 = new Provider(null,"STC", "1294", "Riyadh" , "IT" ,user,null,null,null);
        service1 = new Service(null,"Spring boot","ServiceType","Description of the project, we need a quick and effective solution in this project, and", 990,provider1,null);
        service2 = new Service(null,"Python code","Service Type","Description of the project, we need a quick and effective solution in this project, and", 990,provider1,null);

    }
    @Test
    public void testDeleteProvider() {
        Provider mockProvider = new Provider();
        Mockito.when(providerRepository.findProviderById(provider1.getId())).thenReturn(mockProvider);
        providerService.deleteProvider(provider1.getId());
        Mockito.verify(providerRepository, Mockito.times(1)).delete(mockProvider);
    }
    @Test
    public void testGetAllServicesByProvider() {
        Provider mockProvider = new Provider();
        Set<Service> mockServices = new HashSet<>();
        mockServices.add(new Service());
        mockServices.add(new Service());
        mockProvider.setServices(mockServices);
        Mockito.when(providerRepository.findByCompanyName(provider1.getCompanyName())).thenReturn(mockProvider);
        Set<Service> resultServices = providerService.getAllServicesByProvider(provider1.getCompanyName());
        Assertions.assertThat(resultServices).isEqualTo(mockServices);
    }
    @Test
    public void testGetAllProductsByProvider() {
        Provider mockProvider = new Provider();
        Set<Product> mockProducts = new HashSet<>();
        mockProducts.add(new Product());
        mockProducts.add(new Product());
        mockProvider.setProducts(mockProducts);
        Mockito.when(providerRepository.findByCompanyName(provider1.getCompanyName())).thenReturn(mockProvider);
        Set<Product> resultProducts = providerService.getAllProductsByProvider(provider1.getCompanyName());
        Assertions.assertThat(resultProducts).isEqualTo(mockProducts);
    }
    @Test
    public void testDeleteCompany() {
        Company company1 = new Company();
        Mockito.when(companyRepository.findCompanyById(company.getId())).thenReturn(company1);
        companyService.deleteCompany(company.getId());
        Mockito.verify(companyRepository, Mockito.times(1)).delete(company1);
    }

    @Test
    public void testGetMyCompany() {
        Mockito.when(authRepository.findUserById(user.getId())).thenReturn(company.getUser());
        Company mockCompany = new Company();
        Mockito.when(companyRepository.findCompanyByUser(company.getUser())).thenReturn(mockCompany);
        Company resultCompany = companyService.getMycompany(user.getId());
        Assertions.assertThat(resultCompany).isEqualTo(mockCompany);
    }
}


