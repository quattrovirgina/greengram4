package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class FeedPicsMapperTest {

    private FeedInsDto dtoanother;

    public FeedPicsMapperTest() {
        this.dtoanother = new FeedInsDto();
        this.dtoanother.setIfeed(6);
    }

    @Autowired
    private FeedPicsMapper mapper;

    @BeforeEach
    public void beforeEach() {
        FeedDelDto deldto = new FeedDelDto();
        deldto.setIfeed(this.dtoanother.getIfeed());
        deldto.setIuser(2);
        int affectedRows = mapper.DelFeedPics(deldto);
        System.out.println("delRows : " + affectedRows);
    }
    @Test
    void insFeedPics() {

        List<String> preList = mapper.SelFeedPics(dtoanother.getIfeed());
        assertEquals(0, preList.size());

        List<String> pics = new ArrayList();
        pics.add("a.jpg");
        pics.add("b.jpg");
        pics.add("c.jpg");
        this.dtoanother.setPics(pics);

        int insAffectedRows = mapper.insFeedPics(dtoanother);
        assertEquals(dtoanother.getPics().size(), insAffectedRows);

        List<String> afterList = mapper.SelFeedPics(dtoanother.getIfeed());
        assertEquals(dtoanother.getPics().size(), afterList.size());

        assertEquals(dtoanother.getPics().get(0), afterList.get(0));
        assertEquals(dtoanother.getPics().get(1), afterList.get(1));

        for(int i = 0; i < dtoanother.getPics().size(); i++) {
            assertEquals(dtoanother.getPics().get(i), afterList.get(i));
        }
    }

    @Test
    void selFeedPics() {


    }

    @Test
    void delFeedPics() {
    }
}