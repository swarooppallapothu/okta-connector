package com.poc.connector.service.impl;

import com.poc.connector.config.OktaConfiguration;
import com.poc.connector.mode.Event;
import com.poc.connector.service.EventService;
import com.poc.connector.util.OktaApiEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final OktaConfiguration oktaConfiguration;

    @Autowired
    public EventServiceImpl(OktaConfiguration oktaConfiguration) {
        this.oktaConfiguration = oktaConfiguration;
    }

    @Override
    public List<Event> getEvents() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            URI uri = OktaApiEndpoint.EVENTS.getFullEndpoint(oktaConfiguration.getApiBaseUrl());

            ResponseEntity<String> entity = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(OktaApiEndpoint.EVENTS.getHeaders(oktaConfiguration.getApiKey())),
                    String.class);
            System.out.println(entity.getBody());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return Arrays.asList();
    }
}
