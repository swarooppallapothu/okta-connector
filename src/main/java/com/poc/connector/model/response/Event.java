package com.poc.connector.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Event {

    private String eventId;
    private String sessionId;
    private String requestId;
    private LocalDateTime published;
    private Action action;
    private List<Actor> actors;
    private List<Actor> targets;

}
