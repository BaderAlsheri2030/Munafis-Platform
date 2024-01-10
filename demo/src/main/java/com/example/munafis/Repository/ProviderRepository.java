package com.example.munafis.Repository;


import com.example.munafis.Model.Product;
import com.example.munafis.Model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Integer> {

    Provider findProviderById(Integer id);

  Provider findByCompanyName(String companyName);

}
