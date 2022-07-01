package service;

import exception.ValidationException;

public class ShortPhoneNumberService extends ServiceImpl {
    private static ShortPhoneNumberService shortPhoneNumberService;
    private ShortPhoneNumberService() {}

    public static synchronized ShortPhoneNumberService getInstance() {
        if(shortPhoneNumberService == null)
            shortPhoneNumberService = new ShortPhoneNumberService();
        return shortPhoneNumberService;
    }

    @Override
    public void validate(final String phoneNumber) {
        final int maxSize = 6;
        final int minSize = 4;
        if(phoneNumber.length() < minSize || phoneNumber.length() > maxSize)
            throw new ValidationException("Error: Phone number greater than "+ maxSize + ", or lesser than "+ minSize);
        if(phoneNumber.startsWith("0"))
            throw new ValidationException("Error: Phone number (short) can not start with '0'");
        if(phoneNumber.contains(" "))
            throw new ValidationException("Error: Phone number (short) can not have spaces");
        super.validateSpecialCharacters(phoneNumber);
    }

    public String getCountry(final String phoneNumber){
        return "Portugal";
    }
}
