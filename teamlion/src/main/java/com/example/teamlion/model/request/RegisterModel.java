package com.example.teamlion.model.request;

public class RegisterModel {
    private String username;
    private String password;
    private String email;

    //public Account(int id, String username, String password, String email) {
    // Empty no-arg constructor.
    //}

    public RegisterModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

}
