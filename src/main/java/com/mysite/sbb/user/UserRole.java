package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
    // enum = 열거 자료형
    ADMIN("ROLE_ADMIN"), // 관리자를 의미하는 ADMIN이라는 상수
    USER("ROLE_USER"); // 유저를 의미하는 USER라는 상수
    // 값 수정 필요 없으니 Getter만.

    UserRole(String value){
        this.value = value;
    }

    private String value;
}
