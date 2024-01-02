package com.level3.admin.domain.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminRoleEnum {

    MANAGER(Authority.MANAGER),
    STAFF(Authority.STAFF);

    private final String authority;

    public static class Authority{
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String STAFF = "ROLE_STAFF";
    }
}
