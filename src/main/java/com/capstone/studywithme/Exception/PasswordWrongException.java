package com.capstone.studywithme.Exception;

public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException(){
        super("Password is Wrong");
    }
}
