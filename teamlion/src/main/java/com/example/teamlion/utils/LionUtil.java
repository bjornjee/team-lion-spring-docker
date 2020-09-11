package com.example.teamlion.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.teamlion.utils.model.UtilModel;


import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LionUtil {
	Logger logger = LoggerFactory.getLogger(LionUtil.class);
	
    public String encoder(String username, String password){
    	String strEncode = String.format("%s:%s", username,password);
        String encryptedPW = Base64.getEncoder().encodeToString(strEncode.getBytes());
        return encryptedPW;
    }

    public UtilModel decoder(String encryptedStr){
        byte[] decodedBytes = Base64.getMimeDecoder().decode(encryptedStr);
        String decodedStr = new String(decodedBytes);
        //Check for string structure
        String regex = "[a-zA-Z0-9?-_!]+:[a-zA-Z0-9?-_!]+";
        Pattern r = Pattern.compile(regex);
        Matcher matched = r.matcher(decodedStr);
        logger.info(String.valueOf(matched.matches()));
        //If does not match, return null
        if (!matched.matches()) {
        	return null;
        }
        String[] arr = decodedStr.split(":");
        String user = arr[0];
        String pass = arr[1];

        return new UtilModel(user,pass);

    }

}