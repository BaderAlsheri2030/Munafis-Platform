package com.example.munafis.Repository;


import com.example.munafis.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.munafis.Model.Rfp;

import java.time.LocalDate;
import java.util.Set;


@Repository
public interface RfpRepository extends JpaRepository<Rfp,Integer> {
    Rfp findRfpById(Integer id);
    Set<Rfp> findAllByName(String name);
    Set<Rfp> findAllByLocationEqualsIgnoreCase(String city);
    Set<Rfp> findAllByDeadLineBefore(LocalDate date);
    Set<Rfp> findAllByCompany(Company company);
}
