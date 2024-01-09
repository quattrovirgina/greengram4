package com.green.greengram4.feed.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class FeedSelVo {
    private int ifeed;
    private String contents;
    private String location;
    private String createdAt;
    private int writerIuser;
    private String writerNm;
    private String writerPic;
    private List<String> pics;
    private int isFav; // isFav = 1 좋아요. isFav = 0 좋아요 아님
    private List<FeedCommentSelVo> comments;
    // 이름: comments
    private int isMoreComment; // 0: 댓글 X, 1: 댓글 더있음
}
