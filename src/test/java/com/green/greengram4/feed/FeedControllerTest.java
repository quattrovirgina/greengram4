package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedPicsInsDto;
import com.green.greengram4.feed.model.FeedSelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedController.class)
class FeedControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void postFeed() throws Exception {

        FeedPicsInsDto result = new FeedPicsInsDto();

        //when(service.postFeed(any()));
        given(service.postFeed(any())).willReturn(result);

        //FeedInsDto dto갖고 요청하고 int 타입의 result값을 받는다

        // service.PostFeed(any())라는 가짜 any()를 통해 json 상태로 result를 리턴하도록 할것

        // ResVo vo = service.postFeed(dto);를 검증하기 위한 구문

        // 아무값이나 들어와도 5라는 값으로 리턴하도록 하라

        // picmappertest처럼 두개의 값이 동일한지를 비교하는 구문

        FeedInsDto dto = new FeedInsDto();
        String json = mapper.writeValueAsString(dto);
        System.out.println("json: " + json);

        mvc.perform(
                 MockMvcRequestBuilders

                        .post("/api/feed")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON)
                        // 여기서 json으로 보내고
                        .content(json)
                        // 여기서 응답받아서
        )
                .andExpect(status().isOk())
                // 응답을 여기서 받는지를 확인하라

                .andExpect(content().string(mapper.writeValueAsString(result)))
                // json으로 응답받은 controller에서 ResVo 타입의 vo가 위의 5라는 값이 담긴
                // result와 같은지 비교하도록 하라

                // 위의 5라는 값이 담긴 ResVo result 값을 content()에 응답받은 result와 같다면
                // 스테이터스를 보내고 아니면 오류발생하라

                // 동일한 값이 리턴되어가는지를 확인함을 목적으로 위의 5라는 값이 담긴
                // result와 비교할것
                .andDo(print());

        verify(service).postFeed(any());
    }

    @Test
    void getFeedAll() throws Exception {

        MultiValueMap<String, String> params= new LinkedMultiValueMap();
        params.add("page", "2" );
        params.add("loginnedIser", "4");

        FeedSelVo mk1 = new FeedSelVo();
        mk1.setIfeed(1);
        mk1.setContents("first");
        mk1.setLocation("0079");

        FeedSelVo mk2 = new FeedSelVo();
        mk2.setIfeed(2);
        mk2.setContents("1");
        mk2.setLocation("1");

        FeedSelVo mk3 = new FeedSelVo();
        mk3.setIfeed(3);
        mk3.setContents("1");
        mk3.setLocation("00792");

        // FeedSelVo 타입의 피드를 3개 만들어준다

        List<FeedSelVo> li = new ArrayList<>();
        li.add(mk1);
        li.add(mk2);
        li.add(mk3);

        // List 타입의 li로 위의 저 3개를 담아주고

        given(service.getFeedAll(any())).willReturn(li);
        // given으로 service내에서 getFeedAll에 아무값이나 넣으면 li를 반환하도록 하고

        mvc.perform (
                MockMvcRequestBuilders
                        .get("/api/feed")
                        .params(params)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(li)))
                // 오케이되면 content()내에서 위의 li에 들어있는 값들을
                // String 타입으로 만들어버리고
                .andDo(print());
                // 출력한다

        verify(service).getFeedAll(any());
        // 그리고 검증한다

    }

    @Test
    void vfeed() {
    }

    @Test
    void toggleFeedFav() {

    }

    @Test
    void delFeed() throws Exception {
        ResVo result2 = new ResVo(1);

        given(service.delFeed(any())).willReturn(result2);

        FeedDelDto dto = new FeedDelDto();

        String json2 = mapper.writeValueAsString(dto);

        System.out.println("delete complete");

        mvc.perform (
                MockMvcRequestBuilders
                        .delete("/api/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2)
            )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(result2)))
                .andDo(print());

        verify(service).delFeed(any());
    }
}