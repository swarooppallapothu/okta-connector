package com.poc.connector.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public enum OktaApiEndpoint implements ApiEndpoint {

    EVENTS("events");

    private String pathTemplate;

    OktaApiEndpoint(String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }

    public MultiValueMap<String, String> getHeaders(String apiKey) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "SSWS " + apiKey);
        return headers;
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
