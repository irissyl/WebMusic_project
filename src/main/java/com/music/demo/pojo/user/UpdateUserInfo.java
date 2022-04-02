package com.music.demo.pojo.user;

import lombok.Data;

@Data
public class UpdateUserInfo {
    private long uid;
    private String User_name;
    private String User_avatarUrl;
    private String User_introduction;
    private String User_gender;
    private String User_birthday;
    private String User_address;
}
