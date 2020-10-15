package com.rp.util.application_properties;

import java.io.IOException;

public class ApplicationPropertiesFactory {
    private static final Object LOCK = new Object();
    private static ApplicationProperties INSTANCE;

    public static ApplicationProperties getInstance() throws IOException {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                //INSTANCE = new FileApplicationProperties();
                INSTANCE = new SystemApplicationProperties();
            }

            return INSTANCE;
        }
    }
}
