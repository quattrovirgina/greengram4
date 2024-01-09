package com.green.greengram4.user;

import com.green.greengram4.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignupProcDto pDto);

    UserEntity selUser(UserSelDto dto);

    UserInfoVo SelUserInfo(UserInfoSelDto dto);

    int InsFollow(UserFollowDto dto);

    int DelFollow(UserFollowDto dto);




}
