package com.green.greengram4.dm;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.dm.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class DmService {
    private final DmMapper dmMapper;

    public List<DmSelVo> getDmAll(DmSelDto dto) {
        return dmMapper.selDmAll(dto);
    }
    public List<DmMsgSelVo> getMsgAll(DmMsgSelDto dto) {

        return dmMapper.selDmMsgAll(dto);
    }

    public ResVo postDmMsg(DmMsgInsDto dto) {
        return new ResVo(dmMapper.insDmMsg(dto));
    }

    public ResVo delDmMsg(DmMsgDelDto dto) {
        return new ResVo(dmMapper.delDmMsg(dto));
    }

}
