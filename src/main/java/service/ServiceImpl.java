package service;

import exception.ValidationException;

public abstract class ServiceImpl implements Service {

    protected ServiceImpl() {
    }

    protected void validateSpecialCharacters(final String phoneNumber){
        if(!phoneNumber.matches("^[0-9]*$"))
            throw new ValidationException("Error: Phone number must have only numbers");
    }
}
