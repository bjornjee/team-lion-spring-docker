package com.example.teamlion.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.teamlion.entity.AccountEntity;

import java.util.List;


public interface AccountRepository extends MongoRepository <AccountEntity, String>{

    //@Query("{ id: { $eq: ? } }")
    //List <Account> findById(String id);

    @Query("{ username: { $eq: ?0 } }")
    List <AccountEntity> getByUsername(String username);

    @Query("{ email: { $eq: ?0 } }")
    List <AccountEntity> getByEmail(String email);

    @Query("{ }")
    List <AccountEntity> getAll();

    //get all usernames
    @Query("{ }, {_id = 0, username = 1}")
    List <AccountEntity> getAllUsernames();
    
    @Query("{username:{ $eq: ?0 },password:{ $eq: ?1 }}")
    List<AccountEntity> getOneByUsernameAndPassword(String username, String password);

}

