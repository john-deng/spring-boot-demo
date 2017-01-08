package cn.vpclub.spring.boot.demo.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by johnd on 03/01/2017.
 */
@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
}
