package com.poc.connector.service.impl;

import com.poc.connector.config.OktaConfiguration;
import com.poc.connector.exceptions.InvalidOperation;
import com.poc.connector.model.request.EventRequest;
import com.poc.connector.model.response.Event;
import com.poc.connector.model.response.LoginSummary;
import com.poc.connector.service.EventService;
import com.poc.connector.util.OktaApiConstants;
import com.poc.connector.util.OktaApiEndpoint;
import com.poc.connector.util.OktaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public List<Event> getEvents(EventRequest eventRequest) throws InvalidOperation {
        List<Event> events;
        try {
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            queryParams.add(OktaApiConstants.API_PARAM_EVENT_FILTER, getEventFilterCriteria(eventRequest));
            URI uri = OktaApiEndpoint.EVENTS.getFullEndpoint(getBaseUrl(eventRequest), queryParams);
            ResponseEntity<List<Event>> entity = restTemplate.exchange(
                    uri, HttpMethod.GET, new HttpEntity<>(getHeaders(eventRequest)),
                    new ParameterizedTypeReference<List<Event>>() {
                    });
            events = entity.getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new InvalidOperation("Not able to fetch events with your request");
        }
        return events;
    }

    @Override
    public List<LoginSummary> getLoginSuccessSummary(EventRequest eventRequest) throws InvalidOperation {
        try {
            eventRequest.setActionObjectType("core.user_auth.login_success");
            final List<LoginSummary> loginSummaries = new ArrayList<>();
            getEvents(eventRequest).stream()
                    .map(event -> event.getTargets().get(0).getLogin())
                    .filter(target -> target != null)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .forEach((email, count) -> loginSummaries.add(new LoginSummary(email, count)));
            return loginSummaries;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new InvalidOperation("Not able to fetch events with your request");
        }
    }

}
