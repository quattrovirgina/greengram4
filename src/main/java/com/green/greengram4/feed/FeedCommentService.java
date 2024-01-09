package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedCommentInsDto;
import com.green.greengram4.feed.model.FeedCommentSelDto;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 핵1
@Slf4j
@RequiredArgsConstructor // 핵2
public class FeedCommentService {
    private final FeedCommentMapper mapper; // 핵3

    public ResVo postFeedComment(FeedCommentInsDto dto) {
    // FeedCommentInsDto를 담은 dto 변수를 파라미터로 하고
    // ResVo를 담은 postFeedComment라는 클래스는
        int affectedRows = mapper.insFeedComment(dto);
        // affectedRows라는 int 변수는 dto를 파라미터로 둔 insFeedComment를 통해 mapper로 dto 값을 넘겨준 결과값을 받는다

        return new ResVo(dto.getIfeedComment());
        // 새로운 ResVo를 생성함으로써 dto 내의 getIfeedComment 클래스를 적용한 결과값을 integer로 리턴한다
    }

    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed) {
    // int 형태의 ifeed 변수를 파라미터로 두고
    // FeedCommentSelVo 클래스를 담은 getFeedCommentAll 이라는 List 형태의 클래스는
        FeedCommentSelDto FcDto = new FeedCommentSelDto();
        // FeedCommentSelDto를 담는 FcDto는 새로운 FeedcommentSelDto라는 객체를 생성한다
        FcDto.setIfeed(ifeed);
        // FcDto를 내의 ifeed를 파라미터로 둔 setIfeed를 적용한다
        FcDto.setStartIdx(3);
        //
        FcDto.setRowCount(999);

        return mapper.selFeedCommentAll(FcDto);
    }
}
