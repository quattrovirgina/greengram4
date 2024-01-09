package com.green.greengram4.dm;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.dm.model.*;
import com.green.greengram4.dm.model.DmSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/dm")

public class DmController {

    private final DmService service;

    @GetMapping // 불 꺼져있다면 이거 빼먹은거라고 봐야함
    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return service.getDmAll(dto);
    }
    @GetMapping("/msg")
    public List<DmMsgSelVo> getMsgAll(DmMsgSelDto dto) {
        log.info("dto : {}", dto);
        return service.getMsgAll(dto);

    }
    @PostMapping("/msg")
    public ResVo postDmMsg(@RequestBody DmMsgInsDto dto) {
        return service.postDmMsg(dto);
    }

    @DeleteMapping("/msg")
    public ResVo delDmMsg(DmMsgDelDto dto) {
        return service.delDmMsg(dto);
    }
}
