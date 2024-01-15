package com.green.greengram4.feed;

import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




public class FeedIntegrationTest extends BaseIntegrationTest {
    @Test
    // @Rollback(false)
    public void postFeed() throws Exception {
        FeedInsDto dto = new FeedInsDto();

        dto.setIuser(2);
        dto.setContents("통합 테스트 작업 중임"); // contents로
        dto.setLocation("그린컴터학원"); // location으로

        // reference = 주소값, primitive = 값이 저장됨

        List<String> pics = new ArrayList();
        // dto.setPics(pics);

        pics.add("https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net%2F20221024_297%2F1666622347510e9kgB_JPEG%2F67758131223541321_2059836904.jpg&type=sc960_832");
        pics.add("https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net%2F20221024_297%2F1666622347510e9kgB_JPEG%2F67758131223541321_2059836904.jpg&type=sc960_832");


        String json = om.writeValueAsString(dto);
        System.out.println("json: " + json);

        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON)
                                // 여기서 json으로 보내고
                                .content(json)
                        // 여기서 응답받아서
                )
                .andExpect(status().isOk())
                // 응답을 여기서 받는지를 확인하라
                .andDo(print())
                .andReturn();

        String content = mr.getResponse().getContentAsString();
        ResVo re = om.readValue(content, ResVo.class);
        assertEquals(true, re.getResult() > 0);
    }

    @Test
    @Rollback(false)
    public void delFeed() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "3");
        params.add("iuser", "2");
//
        ResVo vo = new ResVo(1);
        System.out.println("params : " + params);
        mvc.perform( // 오버로딩
                        MockMvcRequestBuilders
                                .delete("/api/feed") // 무슨 Mapping방식인지 지정
                                .params(params)
                ).andExpect(status().isOk()) // 성공했을 때 제대로 리턴됐는지 확인
                .andDo(print())
                .andReturn();
//
        assertEquals(1, vo.getResult());
    }

}
