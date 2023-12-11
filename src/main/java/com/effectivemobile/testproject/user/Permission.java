package com.effectivemobile.testproject.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    EXECUTOR_READ("executor:read"),
    EXECUTOR_UPDATE("executor:update"),
    EXECUTOR_CREATE("executor:create"),
    EXECUTOR_DELETE("executor:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete");

    @Getter
    private final String permission;
}
