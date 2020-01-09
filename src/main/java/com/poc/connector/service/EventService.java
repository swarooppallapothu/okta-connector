package com.poc.connector.service;

import com.poc.connector.model.response.Event;
import com.poc.connector.model.request.EventRequest;

import java.util.List;

public interface EventService {

    List<Event> getEvents(EventRequest Request);
}
