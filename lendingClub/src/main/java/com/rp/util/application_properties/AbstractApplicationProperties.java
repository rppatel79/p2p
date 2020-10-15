package com.rp.util.application_properties;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractApplicationProperties implements ApplicationProperties {
    public Map<String, String> getProperties(String regex) {
        Map<String, String> ret = new HashMap();
        for (Map.Entry<String, String> entry : getProperties().entrySet()) {
            if (entry.getKey().matches(regex))
                ret.put(entry.getKey(), entry.getValue());
        }
        return ret;
    }
}
