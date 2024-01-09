package com.green.greengram4.dm.model;

import lombok.Data;

@Data
public class DmSelVo {
    private int idm; // idm
    private String lastMsg; // last_msg
    private String lastMsgAt; // last_msg_at
    private String otherPersonNm; // t_user >> nm
    private String otherPersonPic; // t_user >> pic
}
