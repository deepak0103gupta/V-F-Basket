package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Query("select a from Address a where a.user.userId = :userId")
    List<Address> findAddressByUserId(@Param("userId") Long userId);

    @Query("select a from Address a where a.addressId = :addressId and a.user.userId = :userId")
    Address findByIdAndUserUserId(Long addressId, Long userId);

}
