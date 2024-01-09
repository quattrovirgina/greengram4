package com.green.greengram4.security;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MyPrincipal { // 토큰 집어넣을때 사용
    private int iuser;
}
