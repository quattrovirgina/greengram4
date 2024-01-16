package com.green.greengram4;

import com.green.greengram4.common.ResVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pic")
public class TestController {
    @GetMapping("/feed/6/https://i.pinimg.com/originals/4a/b8/00/4ab80002ae079ea1c4d432ec81136ae6.gif")
    public ResVo test() {
        return new ResVo(1);
    }
}
