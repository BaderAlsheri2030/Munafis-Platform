package com.example.munafis.Repository;


import com.example.munafis.Model.Company;
import com.example.munafis.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findCompanyByUser(User user);
    Company findCompanyById(Integer id);
}
