package com.V.FBasket.VnFBasket.controller;

import com.V.FBasket.VnFBasket.config.UserInfoUserDetails;
import com.V.FBasket.VnFBasket.model.Address;
import com.V.FBasket.VnFBasket.serviceImpl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vnfbasket")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @PostMapping("/addAddress")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
            Long userId = user.getUserId();
            Address savedAddress = addressService.addAddress(userId, address);
            if (savedAddress != null) {
                return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAllAddressesByUserID")
    public ResponseEntity<List<Address>> getAllAddressesByUserID() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
            Long userId = user.getUserId();
            List<Address> addressList = addressService.getAllAddressByUserID(userId);
            if (addressList != null) {
                return new ResponseEntity<>(addressList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getAddressByAddressId/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        Address address = addressService.getAddressByIdAndUserId(addressId, userId);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        Address updatedAddress = addressService.updateAddress(address, userId);
        if (updatedAddress != null) {
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<Address> deleteAddress(@PathVariable long addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        Address address = addressService.getAddressByIdAndUserId(addressId, userId);
        if(address!=null && addressService.deleteAddress(addressId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
