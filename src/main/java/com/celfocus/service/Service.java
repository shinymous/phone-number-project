package com.celfocus.service;

import java.io.IOException;

public interface Service {
    String getCountry(String phoneNumber) throws IOException;
    void validate(String phoneNumber);
}
