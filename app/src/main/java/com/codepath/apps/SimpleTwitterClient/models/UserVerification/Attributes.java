package com.codepath.apps.SimpleTwitterClient.models.UserVerification;

import java.util.HashMap;
import java.util.Map;

public class Attributes {

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
