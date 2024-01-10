package com.example.munafis.Repository;


import com.example.munafis.Model.Orderr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Orderr,Integer> {

Orderr findOrderrById(Integer id);

List<Orderr> findAllByStatusEqualsAndCompanyId(String Status,Integer company_id);

List<Orderr> findAllByStatusEqualsIgnoreCase(String stat);


    @Query("select o from Orderr o where o.status= 'accepted'")
    Set<Orderr> getMyOrdersByStatusAccepted();
    @Query("select o from Orderr o where o.status= 'completed'")
    Set<Orderr> getMyOrdersStatusCompleted();
    @Query("select o from Orderr o where o.status= 'pending'")
    Set<Orderr> getMyOrdersStatusPending();
}
