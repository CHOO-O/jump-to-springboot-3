package com.mysite.sbb;

import lombok.Getter;
//import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
//@Setter
public class HelloLombok {
//    private String hello;
//    private int lombok;
    private final String hello;
    private final int lombok;

    /*
    만약 Lombok이 없어서 Getter, Setter를 직접 작성해야 했다면?

    public void setHello(String hello){
        this.hello = hello;
    }

    public void setLombok(int lombok){
        this.lombok = lombok;
    }

    public String getHello(){
        return this.hello;
    }

    public int getLombok(){
        return this.lombok;
    }

    얘네를 다 작성해줬어야 하는거임!!!
    */

    public static void main(String[] args){
        HelloLombok helloLombok = new HelloLombok("헬로", 5);
//        helloLombok.setHello("헬로");
//        helloLombok.setLombok(5);

        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
