package com.poc.connector.service;

import com.poc.connector.exceptions.InvalidOperation;
import com.poc.connector.model.request.EventRequest;
import com.poc.connector.model.response.Event;
import com.poc.connector.model.response.LoginSummary;

import java.util.List;

public interface EventService {

    List<Event> getEvents(EventRequest eventRequest) throws InvalidOperation;

    List<LoginSummary> getLoginSuccessSummary(EventRequest eventRequest) throws InvalidOperation;
}
