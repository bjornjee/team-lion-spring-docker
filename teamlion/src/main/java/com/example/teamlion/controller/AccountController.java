package com.example.teamlion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.teamlion.entity.AccountEntity;
import com.example.teamlion.model.request.RegisterModel;
import com.example.teamlion.model.response.RegisterResponseModel;
import com.example.teamlion.service.AccountService;

//import java.net.URI;
import java.util.Collection;

@Controller
@RequestMapping(value="/api/account/")
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    //Get all accounts
    @GetMapping(value="/", produces={"application/json", "application/xml"})
    public ResponseEntity<Collection<AccountEntity>> getAllAccounts(){
        Collection<AccountEntity> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok().body(accounts);
    }

    //Get a specific account
    @GetMapping(value="/{username}", produces={"application/json", "application/xml"})
    public ResponseEntity<AccountEntity> getAccount(@PathVariable String username){
    	AccountEntity account = accountService.getAccount(username);
        if(accountService.getAccount(username) == null){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(account);
        }
    }

    //Insert an account
    @PostMapping(value="/register", consumes={"application/json", "application/xml"}, produces={"application/json", "application/xml"})
    public ResponseEntity<RegisterResponseModel> addAccount(@RequestBody RegisterModel registration){
        String token = accountService.createNewUser(registration);
        //URI uri = URI.create("/item/" + account.getId());
        return ResponseEntity.ok().body(new RegisterResponseModel(registration,token));
    }

    //Update an existing account
    @PutMapping(value="/{username}", consumes={"application/json","application/xml"})
    public ResponseEntity modifyAccount(@PathVariable String username, @RequestBody RegisterModel registration){
        if (accountService.getAccount(username) == null)
            return ResponseEntity.notFound().build();
        else {
            accountService.updateUser(username, registration);
            return ResponseEntity.ok().build();
        }
    }

    //delete an existing account
    @DeleteMapping("/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        if(accountService.getAccount(username) == null){
            return ResponseEntity.notFound().build();
        } else {
            accountService.delete(username);
            return ResponseEntity.ok().build();
        }
    }

    //login encryption





}