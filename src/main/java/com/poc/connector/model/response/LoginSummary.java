package com.poc.connector.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSummary {
    private String email;
    private Long count;
}
