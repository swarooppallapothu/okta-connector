package com.poc.connector.service.impl;

import com.poc.connector.config.OktaConfiguration;
import com.poc.connector.model.request.EventRequest;
import com.poc.connector.model.response.Event;
import com.poc.connector.service.EventService;
import com.poc.connector.service.OktaUtils;
import com.poc.connector.util.OktaApiEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class EventServiceImpl extends OktaUtils implements EventService {

    private final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final OktaConfiguration oktaConfiguration;

    @Autowired
    public EventServiceImpl(OktaConfiguration oktaConfiguration) {
        super(oktaConfiguration);
        this.oktaConfiguration = oktaConfiguration;
    }

    @Override
    public List<Event> getEvents(EventRequest eventRequest) {
        List<Event> events = Arrays.asList();
        try {
            RestTemplate restTemplate = new RestTemplate();

            URI uri = OktaApiEndpoint.EVENTS.getFullEndpoint(getBaseUrl(eventRequest));
            ResponseEntity<List<Event>> entity = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(getHeaders(eventRequest)),
                    new ParameterizedTypeReference<List<Event>>() {
                    });
            events = entity.getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return events;
    }
}
