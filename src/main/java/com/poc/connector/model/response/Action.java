package com.poc.connector.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Action {
    private String message;
    private List<String> categories;
    private String objectType;
    private String requestUri;
}
