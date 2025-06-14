package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller // HelloController 클래스가 컨트롤러의 기능을 수행한다는 것을 알림
//public class HelloController { // 껍데기 클래스 선언
//    @GetMapping("/hello") // GET방식의 URL /hello URL과 hello 메서드를 매핑함. *URL명과 메서드명이 동일할 필요는 없음
//    @ResponseBody // hello 메서드의 출력값 그대로 리턴할 것임을 알림
//
//    public String hello() {
//        return "Hello SBB";
//    }
//}

@Controller
public class HelloController {
    @GetMapping("/jump")
    @ResponseBody

    public String jump() {
        return "점프 투 스프링 부트";
    }
}