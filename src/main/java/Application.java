import facade.PhoneNumberFacade;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        PhoneNumberFacade.process(args[0]);
    }
}
