package com.poc.connector.util;

import com.poc.connector.config.OktaConfiguration;
import com.poc.connector.model.request.EventRequest;
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

    public String getEventFilterCriteria(EventRequest eventRequest) {

        String publishedPredicate = "";
        String actionObjectTypePredicate = "";
        if (!Objects.isNull(eventRequest.getPublishedFrom()) && !Objects.isNull(eventRequest.getPublishedTo())) {
            publishedPredicate = String.format("(published gt \"%s\" and published lt \"%s\")", eventRequest.getPublishedFrom(), eventRequest.getPublishedTo());
        } else if (!Objects.isNull(eventRequest.getPublishedFrom())) {
            publishedPredicate = String.format("published gt \"%s\"", eventRequest.getPublishedFrom());//.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")), .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        } else if (!Objects.isNull(eventRequest.getPublishedTo())) {
            publishedPredicate = String.format("published lt \"%s\"", eventRequest.getPublishedTo());
        }

        if (!StringUtils.isEmpty(eventRequest.getActionObjectType())) {
            actionObjectTypePredicate = String.format("action.objectType eq \"%s\"", eventRequest.getActionObjectType());
        }

        String filterPredicate = "";
        if (!StringUtils.isEmpty(publishedPredicate) && !StringUtils.isEmpty(actionObjectTypePredicate)) {
            filterPredicate = String.join(" and ", publishedPredicate, actionObjectTypePredicate);
        } else if (!StringUtils.isEmpty(publishedPredicate)) {
            filterPredicate = publishedPredicate;
        } else if (!StringUtils.isEmpty(actionObjectTypePredicate)) {
            filterPredicate = actionObjectTypePredicate;
        }
        return filterPredicate;
    }
}
