package com.example.teamlion.utils;


import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.example.teamlion.utils.model.UtilModel;


import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class LionUtil {

    //encrypt the username and password and return token
    public String encoder(String username, String password){
        String strEncode = String.format("%s:%s", username,password);
        String encryptedPW = Base64.getEncoder().encodeToString(strEncode.getBytes());
        return encryptedPW;
    }

    //decrypt to check if its registered user
    public UtilModel decoder(String encryptedStr){
        byte[] decodedBytes = Base64.getMimeDecoder().decode(encryptedStr);
        String decodedStr = new String(decodedBytes);
        //Check for string structure
        String regex = "[a-zA-Z0-9?-_!]+:[a-zA-Z0-9?-_!]+";
        Pattern r = Pattern.compile(regex);
        Matcher matched = r.matcher(decodedStr);
        log.info(String.valueOf(matched.matches()));
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