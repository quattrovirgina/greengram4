package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedCommentInsDto;
import com.green.greengram4.feed.model.FeedCommentSelDto;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import com.green.greengram4.feed.model.FeedDelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 삭제할시 댓글 먼저 지워놓을것

@Mapper // 핵
public interface FeedCommentMapper {
    int insFeedComment(FeedCommentInsDto dto);
    // FeedCommentInsDto를 참조하는 dto를 파라미터로 하여
    // insFeedComment라는 int 객체에 dto에서 입력받은
    // int 형태의 ifeedComment, iuser, ifeed, comment로 나눠 저장할것
    List<FeedCommentSelVo> selFeedCommentAll(FeedCommentSelDto dto);
    // FeedCommentSelDto를 참조하는 dto는
    // 만약 all이라고 써있다면 List<> 붙여놓을것
    int delFeedComment(FeedDelDto dto);
    // int delFeedCommentAll(FeedDelDto dto);
}

// insFeedComment
// select 에서는 vo와 dto가 관여. sql 쿼리 실행시 vo에다 저장되고, dto는 #{}가 채워져있는 값들을 저장
