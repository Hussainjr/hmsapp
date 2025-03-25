package com.hmsapp.controller;

import com.hmsapp.entity.Account;
import com.hmsapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @Autowired private AccountRepository accountRepository;

    @RequestMapping("/account")
    public String getAccount(@RequestBody Account account){
        accountRepository.save(account);
        return "this is an account resource";
    }

    @PutMapping("/a1/account")
    public String updateAccount(@RequestBody Account account){
        accountRepository.save(account);
        return "this is an account resource";
    }



}
