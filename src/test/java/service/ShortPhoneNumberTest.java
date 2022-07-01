package service;

import exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.creator.PhoneNumberCreator.*;

public class ShortPhoneNumberTest {

    private final static ShortPhoneNumberService service = ShortPhoneNumberService.getInstance();

    @Test
    void givenShortPhoneNumber_whenGetCountry_thenReturnCountry(){
        String country = service.getCountry(validShortPhoneNumber);
        assertEquals("Portugal", country);
    }

    @Test
    void givenInvalidShortPhoneNumber_whenValidate_thenThrowsValidationException(){
        final var exception = assertThrows(ValidationException.class, () -> service.validate(invalidShortPhoneNumber));
        assertEquals(ValidationException.class, exception.getClass());
    }
}
