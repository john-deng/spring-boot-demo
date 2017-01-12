package cn.vpclub.spring.boot.demo.service;

/**
 * Foo
 * Created by johnd on 10/01/2017.
 */
public interface IFoo<T> {

    void set(T t);

    T get();
}

