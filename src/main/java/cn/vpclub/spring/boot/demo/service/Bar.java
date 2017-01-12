package cn.vpclub.spring.boot.demo.service;

import lombok.Getter;
import lombok.Setter;

/**
 * Foo
 * Created by johnd on 10/01/2017.
 */
@Getter
@Setter
public class Bar {
    // T stands for "Type"
    private String bar;

    public Bar(String bar) {
        this.bar = bar;
    }
}

