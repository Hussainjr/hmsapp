package com.hmsapp.service;

import com.hmsapp.dto.LoginDto;
import com.hmsapp.dto.UserDto;
import com.hmsapp.entity.User;
import com.hmsapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private JWTService jwtService;
    @Autowired private ModelMapper modelMapper;

    public String saveUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return "user already exists";
        }

        Optional<User> opUserEmail = userRepository.findByEmail(user.getEmail());
        if(opUserEmail.isPresent()){
            return "Email already exists";
        }

        Optional<User> opUserMobile = userRepository.findByMobile(user.getMobile());
        if(opUserMobile.isPresent()){
            return "mobile number is already exists";
        }

        // Hash the password using BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);

        user.setRole("ROLE_USER");
        User savedUser = userRepository.save(user);
        return "User successfully registered with username: " + savedUser.getUsername();
    }

    public String savePropertyOwner(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return "user already exists";
        }

        Optional<User> opUserEmail = userRepository.findByEmail(user.getEmail());
        if(opUserEmail.isPresent()){
            return "Email already exists";
        }

        Optional<User> opUserMobile = userRepository.findByMobile(user.getMobile());
        if(opUserMobile.isPresent()){
            return "mobile number is already exists";
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashedPassword);
        user.setRole("ROLE_PROPERTY_OWNER");
        User savedUser = userRepository.save(user);
        return "User successfully registered with username: " + savedUser.getUsername();
    }


    public String verifyLogin(LoginDto loginDto){
        Optional<User> opuser = userRepository.findByUsername(loginDto.getUsername());
        if (!opuser.isPresent()) {
            // User not found, return null or appropriate message
            return "User not found with username: " + loginDto.getUsername();
        }
        User user = opuser.get();

        if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            return token;
        }else {
            return null;
        }
    }

}
