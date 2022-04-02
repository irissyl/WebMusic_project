package com.music.demo.pojo.user;

import lombok.Data;

@Data
public class RegisterStatusAndLoginStatus {
    private boolean Status;
    private String code;
    private String Content;
    private Object RequestBody;
}
