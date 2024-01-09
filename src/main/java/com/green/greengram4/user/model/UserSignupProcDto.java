package com.green.greengram4.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class UserSignupProcDto {
    private int iuser;
    private String uid;
    private String upw;
    private String nm;
    private String pic;

}
