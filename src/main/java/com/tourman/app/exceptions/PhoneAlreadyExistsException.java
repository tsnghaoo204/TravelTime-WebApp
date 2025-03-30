package com.tourman.app.exceptions;

public class PhoneAlreadyExistsException extends RuntimeException{
    public PhoneAlreadyExistsException(){
        super("Phone Already Exists");
    }
}
