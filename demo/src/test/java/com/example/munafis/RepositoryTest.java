package com.example.munafis;

import com.example.munafis.Model.*;
import com.example.munafis.Repository.CompanyRepository;
import com.example.munafis.Repository.ProductRepository;
import com.example.munafis.Repository.ProviderRepository;
import com.example.munafis.Repository.ServiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ProductRepository productRepository;

    User user1, user2;
    Company company;

    Provider provider,provider1;

    Company company1,company2,company3;
    List<Company> companies;
    Service service;


    @BeforeEach
    void setUp(){
        user1 = new User(null,"username","1234","user@gmail.com","PROVIDER",null,null);
        //user = new User(null,"username","1234","user@gmail.com","PROVIDER",null,null);
        company1 = new Company(null,"apple", "123","apple@gmail.com" ,null,null,null);
        company2 = new Company(null,"google", "222","google@gmail.com" ,null,null,null);
        company3 = new Company(null,"companyUsername1", "333","COMPANY3@gmail.com" ,null,null,null);
        provider1 = new Provider(null,"providerName","provider@gmil.com","ProviderBusinessNumber","ProviderAddress",null,null,null,null);
        service = new Service(null,"ServiceName","ServiceType","ServiceDetailes", 990,provider1,null);


    }


    @Test
    public void testFindAllProductsByProviderId() {
        List<Product> products = productRepository.findAllByProviderId(provider1.getId());
        Assertions.assertThat(products).isNotNull();
    }
    @Test
    public void findCompanyByUser(){
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
    }

    @Test
        public  void findCompanyById(){
        companyRepository.save(company1);
        company=companyRepository.findCompanyById(company1.getId());
        Assertions.assertThat(company).isEqualTo(company1);
}
    @Test
    public void testFindAllByServiceName() {
        List<Service> services = serviceRepository.findServicesByServiceName(service.getServiceName());
        Assertions.assertThat(services).isNotNull();
    }

    @Test
    public  void testFindServiceById(){
        serviceRepository.save(service);
    }

        @Test
        public void testFindProviderById(){
        providerRepository.save(provider1);
        provider=providerRepository.findProviderById(provider1.getId());
        Assertions.assertThat(provider).isEqualTo(provider1);
}




}





