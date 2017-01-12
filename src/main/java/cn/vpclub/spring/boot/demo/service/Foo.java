package cn.vpclub.spring.boot.demo.service;

/**
 * Foo
 * Created by johnd on 10/01/2017.
 */
public class Foo<T> implements IFoo<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}

