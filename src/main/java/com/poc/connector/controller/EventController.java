package com.poc.connector.controller;

import com.poc.connector.model.response.Event;
import com.poc.connector.model.request.EventRequest;
import com.poc.connector.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/okta/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Event>> message(EventRequest request) {
        return new ResponseEntity<List<Event>>(eventService.getEvents(request), HttpStatus.OK);
    }

}
