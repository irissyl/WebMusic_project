package com.music.demo.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseInfo {
    /**
     * User_id;用户id（用户唯一标识）
     */
    private long uid;
    /**
     * User_account;用户账号
     */
    private String uaccount;
    /**
     * User_password;用户密码
     */
    private String upassword;
    /**
     * User_name;用户名称
     */
    private String User_name;
    /**
     * User_avatarUrl;用户头像url
     */
    private String User_avatarUrl;
    /**
     * User_grade;用户等级
     */
    private int User_grade;
    /**
     * User_introduction;用户简介（100字）
     */
    private String User_introduction;
    /**
     * User_gender;用户性别（0女1男）
     */
    private int User_gender;
    /**
     * User_birthday;用户出生日期的时间戳
     */
    private String User_birthday;
    /**
     * User_address;用户地址
     */
    private String User_address;
    /**
     * User_fans_size;用户的粉丝数
     */
    private long User_fans_size;
    /**
     * User_news_size;用户动态数
     */
    private long User_news_size;
    /**
     * User_createdPlaylistCount;用户创建的歌单数
     */
    private int User_createdPlaylistCount;
}
