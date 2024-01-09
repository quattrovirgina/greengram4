package com.green.greengram4.feed;

import com.green.greengram4.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // 핵
public interface FeedMapper {
    int insFeed(FeedInsDto dto);
    // insFeed라는 아이디를 가진 쿼리에 해당하는 선언문
    // FeedInsDto를 참조하는 dto를 파라미터로 하여
    // 쿼리값을 삽입하라

    List<FeedSelVo> selFeedAll(FeedSelDto dto);
    // selFeedAll이라는 아이디를 가진 쿼리에 해당하는
    // List타입의 선언문
    // FeedSelDto를 참조하는 dto를 파라미터로 하여
    // 배열형태이며 FeedSelVo를 참조하는 selFeedAll에
    // 해당 데이터들을 뽑아놓을것

    int selectiuser(int ifeed);
    // selectiuser 아이디를 가진 쿼리에 해당하는 int 선언문
    // int 형태의 ifeed를 파라미터로 하여
    // 쿼리에서 입력받은 ifeed를 받아올것

    int DelFeed(FeedDelDto dto);
    // DelFeed라는 아이디를 가진 쿼리에 해당하는 int 선언문
    // FeedDelDto 형태의 dto를 파라미터로 하여
    // 쿼리에서 지정한 ifeed를 삭제할것



}
