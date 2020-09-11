package com.example.teamlion.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.teamlion.entity.AccountEntity;
import com.example.teamlion.model.request.LoginModel;
import com.example.teamlion.model.request.RegisterModel;
import com.example.teamlion.repository.AccountRepository;
import com.example.teamlion.utils.LionUtil;

import java.util.List;

import static com.mongodb.client.model.Aggregates.count;

@Slf4j
@Component
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private LionUtil utils;

    //returns a list of all accounts in the database
    public List<AccountEntity> getAllAccounts(){
        return repository.getAll();
    }

    //returns account of a given username
    public AccountEntity getAccount(String username){
        List <AccountEntity> a = repository.getByUsername(username);
        AccountEntity account = a.get(0);
        return account;
    }


    //creates new user from RegisterModel
    public String createNewUser(RegisterModel register){

        //retrieve fields to be inserted
        String username = register.getUsername();
        String password = register.getPassword();
        String email = register.getEmail();

        //check if username exists
        if(checkUsernameisUnique(username)){
            AccountEntity newAccount = new AccountEntity(repository.count()+1,username,password,email);
            repository.save(newAccount);
            log.info("User inserted");
            String message = "There are now " +  String.valueOf(repository.count()) + " users";
            log.info(message);
            return utils.encoder(username, password);
        }

        else{
            //System.out.println("Username already exists");
            log.error("Username already exists. Enter a different username");
            return "";
        }

    }



    //updates all fields for a given username
    public void updateUser(String username, RegisterModel register){

        //fetch new data
        String newUsername = register.getUsername();
        String newPassword = register.getPassword();
        String newEmail = register.getEmail();

        List <AccountEntity> a = repository.getByUsername(username);
        AccountEntity account = a.get(0);
        account.setEmail(newEmail);
        account.setPassword(newPassword);
        account.setUsername(newUsername);
        repository.save(account);
        log.info("User updated");

    }


    //deletes account of a given username
    public void delete(String username){
        List <AccountEntity> a = repository.getByUsername(username);
        AccountEntity account = a.get(0);
        repository.delete(account);
        log.info("User deleted");
        String message = "There are now " +  String.valueOf(repository.count()) + " users";
        log.info(message);
    }


    //check if username is unique
    public boolean checkUsernameisUnique(String username){
        List <AccountEntity> a = repository.getByUsername(username);
        if(a.isEmpty())
            return true;
        else
            return false;
    }

    //check if password is correct
    public boolean checkPassword(LoginModel login){
        String username = login.getUsername();
        List <AccountEntity> a = repository.getByUsername(username);
        AccountEntity account = a.get(0);
        if(login.getPassword().equals(account.getPassword())){
            return true;
        }
        else{
            return false;
        }
    }
}





