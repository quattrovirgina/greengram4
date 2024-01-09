package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// yaml에 있는거 조금도 안바꾸고 그대로 쓸거임

public class FeedMapperTest {

    @Autowired
    private FeedFavMapper mapper;
    // AutoWire는 final이 붙인 빈등록된 객체의 주소값을 요청할것?


    @Test
    public void insFeedFav() {
    }

    @Test
    public void delFeedFav() {
        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(129);
        dto.setIuser(4);

        int affectedRows1 = mapper.DelFeedFav(dto);
        assertEquals(1, affectedRows1);

        int affectedRows2 = mapper.DelFeedFav(dto);
        assertEquals(0, affectedRows2);

        List<FeedFavDto> result2 = mapper.selFeedFavForTest(dto);
        assertEquals(0, result2.size());
    }

    @Test
    public void delFeedFavAllTest() {
        final int ifeed = 16;

        FeedFavDto dto = new FeedFavDto();
        dto.setIfeed(ifeed);
        List<FeedFavDto> selList = mapper.selFeedFavForTest(dto);

        FeedDelDto dto2 = new FeedDelDto();
        dto2.setIfeed(16);

        int delAffectedRows1 = mapper.delFeedFavAllTest(dto2);
        assertEquals(selList.size(), delAffectedRows1);

        List<FeedFavDto> selList2 = mapper.selFeedFavForTest(dto);
        assertEquals(0, selList2.size());
    }
}