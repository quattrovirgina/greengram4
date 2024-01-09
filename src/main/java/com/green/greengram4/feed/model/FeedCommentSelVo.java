package com.green.greengram4.feed.model;

import lombok.Data;

@Data

public class FeedCommentSelVo {
    private int ifeedComment;
    private String comment;
    private String writerIuser;
    private String writerNm;
    private String writerPic;
    private String createdAt;
}
