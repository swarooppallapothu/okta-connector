package com.poc.connector.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.emptyMap;

public interface ApiEndpoint {

    String getPathTemplate();

    default URI getFullEndpoint(String baseUrl) {
        return getFullEndpoint(baseUrl, emptyMap());
    }

    default URI getFullEndpoint(String baseUrl, Map<String, String> pathVariables) {
        return getFullEndpoint(baseUrl, pathVariables, new LinkedMultiValueMap<>());
    }

    default URI getFullEndpoint(String baseUrl, MultiValueMap<String, String> queryParams) {
        return getFullEndpoint(baseUrl, emptyMap(), queryParams);
    }

    default URI getFullEndpoint(String baseUrl, Map<String, String> pathVariables, MultiValueMap<String, String> queryParams) {
        UriTemplate template = new UriTemplate(baseUrl + (baseUrl.endsWith("/") ? "" : "/") + getPathTemplate());
        try {
            return new URI(UriComponentsBuilder
                    .fromHttpUrl(template.expand(pathVariables).toString())
                    .queryParams(queryParams)
                    .toUriString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(format("could not build %s url", this.getClass()), e);
        }
    }

    default URI getFullEndpoint(String baseUrl, Object... pathVariables) {
        UriTemplate template = new UriTemplate(baseUrl + getPathTemplate());
        return template.expand(pathVariables);
    }
}
