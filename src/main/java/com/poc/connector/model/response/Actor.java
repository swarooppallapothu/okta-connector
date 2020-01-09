package com.poc.connector.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Actor {
    private String id;
    private String displayName;
    private String login;
    private String objectType;
    private String ipAddress;
}
