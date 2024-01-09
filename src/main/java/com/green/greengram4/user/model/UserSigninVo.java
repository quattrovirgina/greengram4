package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UserSigninVo {
    private int result; // 1) 아이디없음 2) 비번틀림 3) 성공
    private int iuser;
    private String nm;
    private String pic;
    private String firebaseToken;
    private String accessToken;
}
