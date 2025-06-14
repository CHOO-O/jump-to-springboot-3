package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    // 용도 : DB에서 특정 엔티티 혹은 데이터를 찾지 못했을 때 발생시키는 예외 클래스
    // HTTP 상태 코드와 이유를 포함한 응답을 넘기도록 되어있음. (404)
    public DataNotFoundException(String message){
        super(message);
    }
}
