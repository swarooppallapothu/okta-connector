package com.poc.connector.service;

import com.poc.connector.config.OktaConfiguration;
import com.poc.connector.model.request.OktaBaseRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class OktaUtils {

    private final OktaConfiguration configuration;

    public OktaUtils(OktaConfiguration configuration) {
        this.configuration = configuration;
    }

    public String getBaseUrl(OktaBaseRequest request) {
        if (Objects.isNull(request)) {
            return configuration.getApiBaseUrl();
        }
        return String.format("%s/%s/%s", request.getDomain(), "api", configuration.getApiVersion());
    }

    public MultiValueMap<String, String> getHeaders(OktaBaseRequest request) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "SSWS " + (!StringUtils.isEmpty(request) ? request.getApiKey() : configuration.getApiKey()));
        return headers;
    }
}
