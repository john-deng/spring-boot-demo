package cn.vpclub.spring.boot.demo.service;

/**
 * AClassWithPrivateMethod
 * Created by johnd on 07/01/2017.
 */
public class AClassWithPrivateConstructor {

    private AClassWithPrivateConstructor() {}

    public String echoString(String s) {
        return "echo from a class with private constructor: " + s;
    }
}
