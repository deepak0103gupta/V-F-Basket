package com.V.FBasket.VnFBasket.service;

import java.util.List;

import com.V.FBasket.VnFBasket.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User registerUser(User user);
    User updateUser(Long id, User user);
    String deleteUser(Long id);
    List<User> getUsersByRole(String role);
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
    User getUserByEmail(String email);
    boolean checkUserExistsByEmail(String email);
} 
