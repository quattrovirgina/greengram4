package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserFollowDto {
    private int toIuser;
    private int fromIuser;
}
