package com.rvi.analyzer.rvianalyzerserver.domain;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
    private String source;
}
