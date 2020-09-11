package com.example.teamlion.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "account")
public class AccountEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;


    private String username;
    private String password;
    private String email;
}

