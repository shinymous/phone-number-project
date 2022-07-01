package com.celfocus.facade;

import com.celfocus.exception.ValidationException;
import com.celfocus.service.LongPhoneNumberService;
import com.celfocus.service.Service;
import com.celfocus.service.ShortPhoneNumberService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhoneNumberFacade {

    private static final String COUNTRY_FILE_NAME = "coutryCodes.txt";

    public static void process(final String inputFileName) throws IOException {
        BufferedReader inputFileReader = new BufferedReader(new FileReader(inputFileName));
        Map<String, Integer> countryPhoneNumberCountMap = new HashMap<>();
        String line;
        int lineNumber = 1;
        while ((line = inputFileReader.readLine()) != null) {
            System.out.printf("%s%n%s: %s%n", "-----------------------------------------------------------------","Start processing line", lineNumber);
            System.out.printf("%s: %s%n", "Phone number", line);
            line = line.trim();

            Service service = getService(line);
            String country;
            try{
                service.validate(line);
                country = service.getCountry(line);
            }catch (ValidationException e){
                System.out.println(e.getMessage());
                country = "Validation Error";
            }
            countryPhoneNumberCountMap.merge(country, 1, Integer::sum);

            System.out.printf("%s%n%s%n", "Done!", "-----------------------------------------------------------------");
            lineNumber++;
        }

        countryPhoneNumberCountMap.entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .forEach(mapEntry -> System.out.printf("%s: %s%n", mapEntry.getKey(), mapEntry.getValue()));
    }

    public static Service getService(String phoneNumber){
        if(LongPhoneNumberService.removeUncountedCharacters(phoneNumber).length() > 6)
            return LongPhoneNumberService.getInstance(COUNTRY_FILE_NAME);
        return ShortPhoneNumberService.getInstance();
    }
}
