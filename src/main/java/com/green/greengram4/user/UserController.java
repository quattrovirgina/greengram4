package com.green.greengram4.user;


import com.green.greengram4.common.ResVo;
import com.green.greengram4.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController // 콘트롤러의 핵1
@RequiredArgsConstructor // 콘트롤러의 핵2
@RequestMapping("/api/user") // 이거 없어도 자동으로 활성화 되어있음
@Slf4j // log.info 쓰려면 이거 있어야할것
public class UserController {
    private final UserService service; // 콘트롤러의 핵3

    @PostMapping("/signup")
    public ResVo postSignup(@RequestBody UserSignupDto dto) {
        log.info("dto: {}", dto);

        return service.signup(dto);
    } // 회원가입

    @PostMapping("/signin")
    @Operation(summary = "테스트", description = "아이디 및 비번을 활용한 인증처리")
    public UserSigninVo postSignin(@RequestBody UserSigninDto dto) {
            log.info("dto: {}", dto);
            return service.signin(dto);
    }
    // 로그인
    // 소재: pk, 이름, 프로필사진

    //
    @PostMapping("/follow")
    public ResVo toggleFollow(@RequestBody UserFollowDto dto) {
        log.info("dto: {}", dto);
        return service.toggleFollow(dto);
    }
    // 팔로우 하기

    @GetMapping
    public UserInfoVo getUserInfo(UserInfoSelDto dto) {
        return service.getUserInfo(dto);
    }
    // 사용자 정보 가져옴
}

