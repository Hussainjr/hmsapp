package com.hmsapp.controller;
import com.hmsapp.dto.JwtToken;
import com.hmsapp.dto.LoginDto;
import com.hmsapp.dto.ProfileDto;
import com.hmsapp.dto.UserDto;
import com.hmsapp.entity.User;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.JWTService;
import com.hmsapp.service.OTPService;
import com.hmsapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        @Autowired private UserService userService;
        @Autowired private ModelMapper modelMapper;
        @Autowired private OTPService otpService;
        @Autowired private JWTService jwtService;

        private UserRepository userRepository;
        public AuthController(UserRepository userRepository){
            this.userRepository=userRepository;
        }

        //Used for user sign-up
        @PostMapping("/sign-up")
        public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
            String saveUser = userService.saveUser(userDto);
            return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
        }


    //used for a property owner
    @PostMapping("/propertyOwner/sign-up")
    public ResponseEntity<?> createPropertyOwner(@RequestBody UserDto userDto){
        String savedPropertyOwner = userService.savePropertyOwner(userDto);
        return new ResponseEntity<>(savedPropertyOwner,HttpStatus.CREATED);
    }



    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createBlogManagerAccount(@RequestBody User user){

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("user already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opUserEmail = userRepository.findByEmail(user.getEmail());
        if(opUserEmail.isPresent()){
            return new ResponseEntity<>("Email already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opUserMobile = userRepository.findByMobile(user.getMobile());
        if(opUserMobile.isPresent()){
            return new ResponseEntity<>("mobile number is already exists",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        user.setRole("ROLE_BLOGMANAGER");
        User saveUser = userRepository.save(user);
        return new ResponseEntity<>(saveUser,HttpStatus.CREATED);

    }


        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
            String token = userService.verifyLogin(loginDto);

            JwtToken jwtToken = new JwtToken();
            jwtToken.setToken(token);
            jwtToken.setType("JWT");

            System.out.println(token);
            if(token!=null){
                return new ResponseEntity<>(jwtToken, HttpStatus.OK);
            }

            return new ResponseEntity<>("Invalid",HttpStatus.INTERNAL_SERVER_ERROR);
        }


        @PostMapping("/login-otp")
        public ResponseEntity<?> loginWithOtp(@RequestParam String mobile){
            String otp = otpService.generateOTP(mobile);

            if(otp!=null){
                return new ResponseEntity<>(otp, HttpStatus.OK);
            }

            return new ResponseEntity<>("invalid",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @PostMapping("/verify-otp")
        public String verifyOtp(@RequestParam String mobile, @RequestParam String otp){
            boolean validateOTP = otpService.validateOTP(mobile, otp);
            if(validateOTP){
                Optional<User> byMobile = userRepository.findByMobile(mobile);
                User user = byMobile.get();
                String token = jwtService.generateToken(user.getUsername());
                return "OTP verified successfully! Token: " + token;
            }else{
                return "Invalid OTP for mobile: " + mobile;
            }

        }

        @GetMapping
        public ResponseEntity<ProfileDto> GetUserProfile(@AuthenticationPrincipal User user){
            ProfileDto dto = modelMapper.map(user, ProfileDto.class);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }



}
