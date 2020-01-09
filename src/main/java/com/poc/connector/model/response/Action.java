package com.poc.connector.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Action {
    private String message;
    private List<String> categories;
    private String objectType;
    private String requestUri;
}
