package util.creator;

import java.util.List;
import java.util.Random;

public class PhoneNumberCreator {

    public static final String validLongPhoneNumber = createValidLongPhoneNumber();
    public static final String validLongPhoneNumberWithSpecialCharacters = createValidLongPhoneNumberWithSpecialCharacters();
    public static final String invalidLongPhoneNumber = createInvalidLongPhoneNumber();
    public static final String validShortPhoneNumber = createValidShortPhoneNumber();
    public static final String invalidShortPhoneNumber = createInvalidShortPhoneNumber();

    public static String createBasePhoneNumber(int minLength, int maxLength){
        Random random = new Random();
        int phoneLength = random.nextInt(maxLength-minLength+1)+minLength;
        StringBuilder phoneNumber = new StringBuilder();
        for(int i = 0; i < phoneLength; i++){
            phoneNumber.append(random.nextInt(9)+1);
        }
        return phoneNumber.toString();
    }

    public static String createValidShortPhoneNumber(){
        return createBasePhoneNumber(5, 6);
    }

    public static String createInvalidShortPhoneNumber(){
        return createBasePhoneNumber(2, 3);
    }

    public static String createValidLongPhoneNumber(){
        return getCountryCode().concat(createBasePhoneNumber(9, 10));
    }

    public static String createValidLongPhoneNumberWithSpecialCharacters(){
        return "+".concat(createValidLongPhoneNumber()).replaceFirst("0", "1");
    }

    public static String createInvalidLongPhoneNumber(){
        return "+ 28 ".concat(createBasePhoneNumber(9, 10));
    }

    public static String getCountryCode(){
        List<String> countryCodes = List.of("353", "351", "44", "1");
        return countryCodes.get(new Random().nextInt(4));
    }
}
