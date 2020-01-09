package com.poc.connector.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public enum OktaApiEndpoint implements ApiEndpoint {

    EVENTS("events");

    private String pathTemplate;

    OktaApiEndpoint(String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }

    @Override
    public String getPathTemplate() {
        return pathTemplate;
    }

    @Override
    public String toString() {
        return pathTemplate;
    }


}
