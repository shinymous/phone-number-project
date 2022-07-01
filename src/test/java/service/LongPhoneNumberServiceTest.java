package service;

import com.celfocus.exception.ValidationException;
import com.celfocus.service.LongPhoneNumberService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static util.creator.PhoneNumberCreator.*;

public class LongPhoneNumberServiceTest {

    private final static LongPhoneNumberService service = LongPhoneNumberService.getInstance("src/test/resources/countryCodes_test.txt");

    @Test
    void givenLongPhoneNumber_whenRemoveUncountedCharacters_thenReturnPhoneWithoutSpecialCharacters(){
        String phoneWithoutSpecialCharacters = LongPhoneNumberService.removeUncountedCharacters(validLongPhoneNumberWithSpecialCharacters);
        assertEquals(validLongPhoneNumberWithSpecialCharacters.length()-1, phoneWithoutSpecialCharacters.length());
    }

    @Test
    void givenPhoneNumber_whenGetCountry_thenReturnCountry() {
        String country = service.getCountry(validLongPhoneNumber);
        assertNotEquals(null, country);
        assertNotEquals("Validation error", country);
        assertNotEquals("Invalid country code", country);
    }

    @Test
    void givenPhoneNumber_whenValidate_thenThrowsValidationException(){
        final var exception = assertThrows(ValidationException.class, () -> service.validate(invalidLongPhoneNumber));
        assertEquals(ValidationException.class, exception.getClass());
    }
}
