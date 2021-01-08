package com.example.techgo;

import java.util.Vector;
import java.util.regex.Pattern;

public class ValidatorAccount {
    public boolean isEmpty(String txt){
        if (txt.length() == 0){
            return true;
        }
        return false;
    }
    public Vector<String> Validator(String email,String password,String confirmPassword,String username){
        Vector<String> error = new Vector<String>();
        String EMAIL_PATTERN =
                "^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
        if (isEmpty(email)){
            error.add("Email is empty");
        }else if (!Pattern.matches(EMAIL_PATTERN, email)){
            error.add("Email is invalid");
        }
        if (isEmpty(password)){
            error.add("Password is empty");
        }else if (password.length() < 6){
            error.add("Password is too short");
        }else if (password != confirmPassword){
            error.add("Password and ConfirmPassword does not match");
        }
        if (isEmpty(username)){
            error.add("Username is empty");
        }
        return error;
    }

}
