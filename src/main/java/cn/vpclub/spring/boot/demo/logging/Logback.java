package cn.vpclub.spring.boot.demo.logging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Logback annotation
 * Created by johnd on 28/12/2016.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Logback {
    /**
     * Sets the category of the constructed Logger. By default, it will use the type where the annotation is placed.
     */
    String topic() default "";
}