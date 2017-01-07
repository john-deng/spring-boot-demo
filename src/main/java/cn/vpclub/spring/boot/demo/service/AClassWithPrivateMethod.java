package cn.vpclub.spring.boot.demo.service;

/**
 * Created by johnd on 07/01/2017.
 */
public class AClassWithPrivateMethod {

    private String initString;

    private AClassWithPrivateMethod(String initString) {
        this.initString = initString;
    }

    private String echoString(String s) {
        return "echo from a class with private method: " + s;
    }

    public String echoString() {
        return "echo from a class with private constructor: " + initString;
    }


}
