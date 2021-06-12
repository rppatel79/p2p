package com.rp.util.application_properties;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersByPathResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;

import java.util.HashMap;
import java.util.Map;

public class AwsPropertyStoreApplicationProperty implements ApplicationProperties {
    private final AWSSimpleSystemsManagement ssm_ = AWSSimpleSystemsManagementClientBuilder.defaultClient();

    public AwsPropertyStoreApplicationProperty() {
    }

    @Override
    public String getProperty(String key) {
        GetParameterRequest request = new GetParameterRequest();
        request.setName(key);
        request.setWithDecryption(false);
        return ssm_.getParameter(request).getParameter().getValue();
    }

    @Override
    public Map<String, String> getProperties() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, String> getProperties(String prefix) {
        GetParametersByPathRequest request = new GetParametersByPathRequest();
        String searchAblePrefix = "/" + prefix;
        request.setPath(searchAblePrefix);
        request.setRecursive(Boolean.TRUE);
        GetParametersByPathResult getParametersByPathResult = ssm_.getParametersByPath(request);

        Map<String, String> ret = new HashMap<>();
        for (Parameter parameter : getParametersByPathResult.getParameters()) {
            String keyWithoutPrefix = parameter.getName().substring(searchAblePrefix.length());
            ret.put(keyWithoutPrefix, parameter.getValue());
        }

        return ret;
    }
}
