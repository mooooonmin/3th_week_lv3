package com.level3.admin.entity;

import lombok.AllArgsConstructor;

//@AllArgsConstructor 생성자 간소화 할 수 있으나 일단 작성
public enum UserRoleEnum {
    STAFF(Authority.STAFF),
    MANAGER(Authority.MANAGER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String STAFF = "ROLE_STAFF";
        public static final String MANAGER = "ROLE_MANAGER";
    }
}
