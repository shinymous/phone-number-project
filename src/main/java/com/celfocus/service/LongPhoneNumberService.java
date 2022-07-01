package com.celfocus.service;

import com.celfocus.exception.CustomException;
import com.celfocus.exception.ValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

public class LongPhoneNumberService extends ServiceImpl {

    private static LongPhoneNumberService longPhoneNumberService;
    private static final int COUNTRY_NAME = 0;
    private static final int COUNTRY_CODE = 1;
    private final String countryFileName;

    private LongPhoneNumberService(final String countryFileName) {
        this.countryFileName = countryFileName;
    }

    @Override
    public void validate(final String phoneNumber){
        final String formattedPhoneNumber = removeUncountedCharacters(phoneNumber);
        final int maxSize = 14;
        final int minSize = 9;
        if(formattedPhoneNumber.length() < minSize || formattedPhoneNumber.length() > maxSize)
            throw new ValidationException("Error: Phone number greater than "+ maxSize + ", or lesser than "+ minSize);
        this.validateTheFirstCharacters(phoneNumber);
        super.validateSpecialCharacters(formattedPhoneNumber);
    }

    @Override
    public String getCountry(final String phoneNumber) {
        final String countryCode = removeUncountedCharacters(phoneNumber).substring(0, 3);
        try {
            BufferedReader countryCodesFileReader = new BufferedReader(new FileReader(this.countryFileName));
            String line;
            String country = null;
            while ((line = countryCodesFileReader.readLine()) != null) {
                line = line.trim();
                final List<String> countryAndCode = List.of(line.split("-"));
                final String code = countryAndCode.get(COUNTRY_CODE);
                if(code.equals(countryCode.substring(0, code.length()))){
                    country = countryAndCode.get(COUNTRY_NAME);
                    break;
                }
            }
            if(isNull(country))
                throw new ValidationException("Country code not found");
            return country;
        }catch (FileNotFoundException e) {
            throw new CustomException("File: "+this.countryFileName+" could not be found");
        }catch (IOException e){
            throw new CustomException("File could not be read");
        }
    }

    public static synchronized LongPhoneNumberService getInstance(final String countryFileName) {
        if(longPhoneNumberService == null)
            longPhoneNumberService = new LongPhoneNumberService(countryFileName);
        return longPhoneNumberService;
    }

    private void validateTheFirstCharacters(final String phoneNumber){
        final char first = phoneNumber.charAt(0);
        final char second = phoneNumber.charAt(1);
        final char third = phoneNumber.charAt(2);
        if(first == '+'){
            if(second == '0')
                throw new ValidationException("Error: Phone number can not start with '+' and '0'");
            if(second == ' ')
                throw new ValidationException("Error: Phone number can not have space between '+' and country code");
        }else if(first == '0'){
            if(second != '0')
                throw new ValidationException("Error: Phone number must start with '00' or '+'");
            if(third == ' ')
                throw new ValidationException("Error: Phone number can not have space between '00' and country code");
            if(third == '0')
                throw new ValidationException("Error: Phone number must have a valid country code");
        }
    }

    public static String removeUncountedCharacters(final String phoneNumber){
        String formattedPhoneNumber = phoneNumber;
        for(int i = 0; i < phoneNumber.length(); i++){
            if(phoneNumber.charAt(i) == '+' || phoneNumber.charAt(i) == '0'){
                formattedPhoneNumber = phoneNumber.substring(i+1);
                continue;
            }
            break;
        }
        return formattedPhoneNumber.replaceAll(" ", "");
    }
}
