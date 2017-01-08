package cn.vpclub.spring.boot.demo.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by johnd on 08/01/2017.
 */
@Setter
@Getter
public class UserResponse implements Serializable {
    private String message;
}
