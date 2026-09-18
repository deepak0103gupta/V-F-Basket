package com.V.FBasket.VnFBasket.serviceImpl;

import java.util.List;
import java.util.Optional;

import com.V.FBasket.VnFBasket.config.UserInfoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.V.FBasket.VnFBasket.dao.UserRepository;
import com.V.FBasket.VnFBasket.model.User;
import com.V.FBasket.VnFBasket.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {
        try{
            return userRepo.findById(id).orElse(null);
        } catch(Exception e){
            e.printStackTrace();}
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        try{
            return userRepo.findAll();
        } catch(Exception e){
            
                e.printStackTrace();
        }
        return null;
    }

    @Override
    public User registerUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            return userRepo.save(user);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        try{

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteUser(Long id) {
        try{
            Optional<User> opt = userRepo.findById(id);
            if (opt.isEmpty()) return "User with id " + id + " not found.";
            userRepo.deleteById(id);
            return "User with id " + id + " deleted successfully.";
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public List<User> getUsersByRole(String role) {

        return userRepo.findByRole(role);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         Optional<User> userInfo = userRepo.findByEmail(email);
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).get();
    }

    @Override
    public boolean checkUserExistsByEmail(String email) {
        return userRepo.existsUserByEmail(email);
    }

}
