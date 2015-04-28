package ca.ulaval.glo4002.applicationServices.locator;

import java.io.IOException;

public interface LocatorModule {

    void load(LocatorContainer container) throws IOException;
}
