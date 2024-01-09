package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedFavDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedFavMapper {
    int insFeedFav(FeedFavDto dto);
    int DelFeedFav(FeedFavDto dto);

    int DelFav(FeedFavDto dto);
    // int delFeedFavAll(FeedDelDto dto);

    List<FeedFavDto> selFeedFavForTest(FeedFavDto dto);

    int delFeedFavAllTest(FeedDelDto dto);


}
