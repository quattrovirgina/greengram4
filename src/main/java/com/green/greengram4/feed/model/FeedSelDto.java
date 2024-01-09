package com.green.greengram4.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram4.common.Const;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class FeedSelDto {
    @Schema(title = "페이지", defaultValue = "1")
    private int page;

    private int loginedIuser;

    @Schema(title = "프로필 주인 pk")
    private int targetIuser;

    private int isFavList; // 안보내면 default 값인 0, 보내면 1

    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int finishIdx = Const.FEED_COUNT_PER_PAGE;

    public void setPage(int page) {
        this.startIdx = (page - 1) * finishIdx;
    }
}

//JsonIgnore 어노테이션으로 박스갈이를 무시할수 있음