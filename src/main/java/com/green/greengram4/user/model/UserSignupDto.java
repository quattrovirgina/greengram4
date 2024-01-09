package com.green.greengram4.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignupDto {
    // iuser는 swagger ui에 미리 적어놓았기 때문에 굳이 여따가 서술할 필요없음
    // 여기서는 보이는것만 보여야하기에 iuser는 서술하지 않는다

    @Schema(title = "유저 아이디")
    // 공백이어도
    private String uid;
    @Schema(title="유저 비번")
    private String upw;
    @Schema(title="유저 이름")
    private String nm;
    @Schema(title="유저 사진")
    private String pic;
}
