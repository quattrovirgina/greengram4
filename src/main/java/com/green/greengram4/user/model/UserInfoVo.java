package com.green.greengram4.user.model;

import lombok.Data;

@Data

public class UserInfoVo {
    private int feedCnt;
    private int favCnt;
    private String nm;
    private String createdAt;
    private String pic;
    private int follower;
    private int following;
    private int followState;
}
