package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    Optional<Payments> findByOrderOrderId(Long orderId);

}
