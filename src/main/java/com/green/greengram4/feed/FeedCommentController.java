package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedCommentInsDto;
import com.green.greengram4.feed.model.FeedCommentSelVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 핵1
@Slf4j
@RequiredArgsConstructor // 핵2
@RequestMapping("/api/feed/comment")
public class FeedCommentController {
    private final FeedCommentService service; // 핵3
    @PostMapping
    public ResVo posFeedComment(@RequestBody FeedCommentInsDto dto) {
    // ResVo를 담을수 있고 FeedcommentInsDto를 담은 dto를 파라미터로 둔 posFeedComment라는 클래스는
        log.info("dto: {}", dto);
        // 출력은 다음과 같이한다
        return service.postFeedComment(dto);
        // service 내에서 dto를 담은 postFeedComment를 리턴한다
    }

    @GetMapping
    // 댓글 4번부터 999번까지의 레코드만 리턴되도록 할것
    public List<FeedCommentSelVo> getFeedCommentAll(int ifeed) {
    // FeedCommentSelVo를 담은 List 타입이고 ifeed라는 integer변수를 파라미터로 두는 getFeedCommentAll
    // 이라는 클래스는
    // service 내에서 ifeed를 파라미터로 하는 getFeedCommentAll 클래스로 ifeed값을 넘겨준다

        return service.getFeedCommentAll(ifeed);
        // 그리고 service 내에서 getFeedCommentAll을 통해 ifeed값을 넘겨줌으로써 리턴한다

    }
}
