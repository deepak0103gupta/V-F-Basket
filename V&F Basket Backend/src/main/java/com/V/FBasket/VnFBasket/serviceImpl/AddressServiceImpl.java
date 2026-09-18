package com.V.FBasket.VnFBasket.serviceImpl;

import com.V.FBasket.VnFBasket.dao.AddressRepository;
import com.V.FBasket.VnFBasket.dao.UserRepository;
import com.V.FBasket.VnFBasket.model.Address;
import com.V.FBasket.VnFBasket.model.User;
import com.V.FBasket.VnFBasket.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Address addAddress(Long userId, Address address) {
        User u1 = userRepo.findById(userId).orElse(null);
        address.setUser(u1);
        return addressRepo.save(address);
    }

    @Override
    public List<Address> getAllAddressByUserID(Long userId) {

        return addressRepo.findAddressByUserId(userId);
    }

    @Override
    public Address getAddressByIdAndUserId(Long addressId, Long userId) {
        return addressRepo.findByIdAndUserUserId(addressId, userId);
    }

    @Override
    public Address updateAddress(Address address, Long userId) {
        Address existingAddress = addressRepo.findByIdAndUserUserId(address.getAddressId(), userId);
        existingAddress.setAddress(address.getAddress());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setPinCode(address.getPinCode());
        if (address.getIsDefault() != null) {
            existingAddress.setIsDefault(address.getIsDefault());
        }
        return addressRepo.save(existingAddress);
    }

    @Override
    public boolean deleteAddress(Long addressId) {
        Optional<Address> opt = addressRepo.findById(addressId);
        if (opt.isEmpty()) return false;
        addressRepo.deleteById(addressId);
        return true;
    }


}
