package com.rp.util.application_properties;

import java.util.Map;

public interface ApplicationProperties {
    String getProperty(String key);

    Map<String, String> getProperties();

    Map<String, String> getProperties(String regex);
}
