package com.example.teamlion.model.response;

import com.example.teamlion.model.request.RegisterModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseModel {
    private String username;
    private String password;
    private String email;
    private String token;
    
    public RegisterResponseModel(RegisterModel model, String token) {
    	this.username = model.getUsername();
    	this.password = model.getPassword();
    	this.email = model.getEmail();
    	this.token = token;
    }
	
}
