package com.rp.util.application_properties;

import java.util.HashMap;
import java.util.Map;

public class SystemApplicationProperties extends AbstractApplicationProperties implements ApplicationProperties {
    private final Map<String, String> systemProperties_ = new HashMap<>();

    SystemApplicationProperties() {
        Map<String, String> systemMap = System.getenv();
        for (Map.Entry<String, String> entry : systemMap.entrySet()) {
            systemProperties_.put(entry.getKey(), entry.getValue());

            // this is a dirty hack because AWS Parameter Store does NOT support '.' in the key
            if (entry.getKey().startsWith("mail."))
                systemProperties_.put(entry.getKey().replace('_', '.'), entry.getValue());
        }
    }

    @Override
    public String getProperty(String key) {
        return systemProperties_.get(key);
    }

    @Override
    public Map<String, String> getProperties() {
        return systemProperties_;
    }
}
