package com.effectivemobile.testproject.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    //@JsonProperty("token")
    private String token;
}
