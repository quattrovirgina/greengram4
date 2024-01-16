package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({FeedService.class})

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedServiceTest {

    @MockBean
    private FeedMapper mapper;

    @MockBean
    private FeedPicsMapper picsMapper;

    @MockBean
    private FeedFavMapper favMapper;

    @MockBean
    private FeedCommentMapper commentMapper;

    @Autowired
    private FeedService service;
    @Test
    void postFeed() {
        when(mapper.insFeed(any())).thenReturn(1);
        // any는 뭔가 주입이 될것. 즉 뭔가 집어넣게되는 상황이올때
        // 1을 리턴하라
        when(picsMapper.insFeedPics(any())).thenReturn(3);
        // picsMapper >> insFeedPics에 무슨 값이 들어온다면 3을 리턴하라

        FeedInsDto dto = new FeedInsDto();
        //ResVo vo = service.postFeed(dto);

        //assertEquals(dto.getIfeed(), vo.getResult());

        verify(mapper).insFeed(any());
        verify(picsMapper).insFeedPics(any());

        FeedInsDto dto2 = new FeedInsDto();
        dto2.setIfeed(200);
        //ResVo vo2 = service.postFeed(dto2);
        //assertEquals(dto2.getIfeed(), vo2.getResult());
    }

    @Test
    public void getFeedAll() {
        FeedSelVo feedSelvo1 = new FeedSelVo();
        // FeedSelVo를 참조하는 feedSelVo1는 새로운 FeedSelVo 객체를 생성한다
        feedSelvo1.setIfeed(1);
        // feedSelVo1 내에 1번이라는 ifeed를 세트한다
        feedSelvo1.setContents("일번 ");
        // feedSelvo1 내에 contents에 "일번"이라고 써넣는다

        FeedSelVo feedSelVo2 = new FeedSelVo();
        // FeedSelVo를 참조하는 feedSelVo2는 새로운 FeedSelVo() 객체를 생성한다
        feedSelVo2.setIfeed(3);
        // feedSelVo2내에 3이라는 ifeed를 세트한다
        feedSelVo2.setContents("이번 ");
        // feedSelVo2내에 contents에다가 "이번"이라고 써넣는다

        List<FeedSelVo> list = new ArrayList();
        // FeedSelVo의 List형태인 list 변수는 새로운 ArrayList형태의 객체를 생성한다

        list.add(feedSelvo1);
        // list에 feedSelvo1를 추가한다
        list.add(feedSelVo2);
        // 뒤이어 feedSelVo2를 추가한다

        when(mapper.selFeedAll(any())).thenReturn(list);
        // 만약 mapper 내에 selFeedAll에 아무 값이 들어온다면 list자체를 반환하도록 하라

        FeedSelDto dto = new FeedSelDto();
        // FeedSelDto를 참조하는 dto는 새로운 FeedSelDto 객체를 생성한다
        List<FeedSelVo> result = service.getFeedAll(dto);
        // FeedSelVo의 List형태인 result는 service 내에 gfeed 클래스를 적용한 dto의 값을 갖는다

        assertEquals(list, result);
        // result와 list가 같은지 확인한다. 맞으면 통과하고 아니면 에러발생하도록 할것

        for (int i = 0; i < result.size(); i++) { // result의 길이 미만까지 i를 증가시키면서

            FeedSelVo rVo = result.get(i); // FeedSelVo를 참조하는 rVo는 result 내에서 get을 적용한 i값을 가진다
            FeedSelVo pVo = list.get(i); // FeedSelVo를 참조하는 pVo는 list 내에서 get을 적용한 i값을 가진다


            assertEquals(pVo.getIfeed(), rVo.getIfeed());
            // pVo 내에 getIfeed를 적용한 값과 rVo 내에 getIfeed를 적용한 값이 같은지를 비교한다
            assertEquals(pVo.getContents(), rVo.getContents());
            // pVo 내에 getContents를 적용한 값과 rVo 내에 getContents를 적용한 값이 같은지 비교한다
        }

        List<String> feed1Pics = Arrays.stream(new String[]{"a.jpg", "b.jpg"}).toList();
        // feedSelvo1.setPics(feed1Pics);
        // List타입의 String feed1Pics 클래스는 String 타입임과 동시에 a.jpg, b.jpg로 구성된 배열을 toList()를 통해 반환하도록 한다

        // stream: 배열을 잡아다가 무슨 일처리를 할수있도록 하는 기능
        List<String> feed2Pics = new ArrayList();
        // List타입의 String feed2Pics는 새로운
        feed2Pics.add("가.jpg");
        feed2Pics.add("나.jpg");

        List<List<String>> picsList = new ArrayList<>();
        // List 형태의 String 타입 picsList는 새로운 ArrayList 객체를 생성하고
        picsList.add(feed1Pics);
        // PicsList에 feed1Pics를 추가하고
        picsList.add(feed2Pics);
        // 뒤이어 feed2Pics를 추가한다

        List<String>[] picsArr = new List[2];
        // List 형태의 String 타입의 picsArr는 길이가 2짜리인 새로운 객체를 생성한다
        picsArr[0] = feed1Pics;
        // picsArr의 0번빼 방에는 feed1Pics를 집어넣고
        picsArr[1] = feed2Pics;
        // picsArr의 1번째 방에는 feed2Pics를 집어넣는다

        when(picsMapper.SelFeedPics(1)).thenReturn(feed1Pics);
        when(picsMapper.SelFeedPics(3)).thenReturn(feed2Pics);

        FeedCommentSelDto dto3 = new FeedCommentSelDto();
        dto3.setIfeed(1);
        dto3.setStartIdx(0);
        dto3.setRowCount(4);

        FeedCommentSelDto dto4 = new FeedCommentSelDto();
        dto4.setIfeed(3);
        dto4.setStartIdx(0);
        dto4.setRowCount(4);

        FeedCommentSelVo comment1 = new FeedCommentSelVo();
        comment1.setComment("rrrrr");

        FeedCommentSelVo comment2 = new FeedCommentSelVo();
        comment2.setComment("dsfasdfasd");

        List<FeedCommentSelVo> lee1 = new ArrayList<>();
        lee1.add(comment1);

        List<FeedCommentSelVo> lee2 = new ArrayList<>();
        lee2.add(comment2);

        when(commentMapper.selFeedCommentAll(dto3)).thenReturn(lee1);
        when(commentMapper.selFeedCommentAll(dto4)).thenReturn(lee2);

        List<FeedCommentSelVo> rim1 = new ArrayList<>();
        rim1.add(comment1);

        List<FeedCommentSelVo> rim2 = new ArrayList<>();
        rim2.add(comment2);

        List<FeedCommentSelVo>[] rim3 = new List[2];

        FeedSelDto dto2 = new FeedSelDto();
        List<FeedSelVo> result2 = service.getFeedAll(dto2);


        assertEquals(list, result2);
        for (int i = 0; i < result2.size(); i++) {
            FeedSelVo vo = list.get(i);
            assertNotNull(vo.getPics());
            System.out.println(vo);

            List<String> pics = picsList.get(i);
            assertEquals(vo.getPics(), pics);

            List<String> pics2 = picsArr[i];
            assertEquals(vo.getPics(), pics2);
        }

        List<FeedCommentSelVo> commentsResult1 = list.get(0).getComments();
        assertEquals(lee1, commentsResult1, "ifeed(1) 댓글은 ");
        assertEquals(0, list.get(0).getIsMoreComment(), " 됐냐 ");
        assertEquals(2, list.get(0).getIsMoreComment(), " 나야 ");

        List<FeedCommentSelVo> commentsResult2 = list.get(1).getComments();
        assertEquals(lee2, commentsResult2, "ifeed(2) 댓글은 ");
        assertEquals(1, list.get(1).getIsMoreComment(), " 뭐해 이놈아 ");
        assertEquals(3, list.get(1).getIsMoreComment(), "잘하겠읍니다");

    }
    @Test
    void gfeed() {
    }

    @Test
    void toggleFeedFav() {
    }

    @Test
    void delFeed() {
    }
}