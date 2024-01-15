package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.List;


@RequiredArgsConstructor
// 핵1
@RestController
// 핵2
@Slf4j
// 핵 2.5
@RequestMapping("/api/feed")
@Tag(name = "피드 API", description = "피드 관련 처리")

public class FeedController {
    private final FeedService service;
    // FeedService를 참조하는 service라는 고정변수를 선언한다
    // 핵 3
    @Operation(summary = "피드 등록", description = "피드 등록 처리")
    @PostMapping
    // public ResVo postFeed(@RequestBody FeedInsDto dto) {
    public ResVo postFeed(@RequestPart List<MultipartFile> pics, @RequestPart FeedInsDto dto) {
        log.info("pics: {}", pics.size());
        log.info("dto: {}", dto);
        /* ResVo vo = service.postFeed(dto);
        System.out.println(vo.getResult());
        return vo; */
        dto.setPics(pics);
        return service.postFeed(dto);

        // return service.postFeed(dto);
    }
    // FeedInsDto를 참조하는 dto를 파라미터로 하고 ResVo를 참조하는 postFeed라는 클래스. 여기서 @RequestBody는
    // 여기서 @RequestBody는 xml에서 받은 데이터를 프론트의 java 객체로 바꿔서 매핑된 메소드 파라미터로 전달한다

    @GetMapping
    @Operation(summary = "피드 리스트", description = "전체 피드 리스트, 특정 사용자 프로필 화면에서 사용할 피드 리스트, 한 페이지 30개 피드 가져옴")
    public List<FeedSelVo> getFeedAll(FeedSelDto dto) { // page라는 int 변수를 파라미터로
        log.info("dto: {}", dto);
        return service.getFeedAll(dto);
        // return service.getFeedAll(dto);
    }

    @GetMapping("/fav")
    public ResVo toggleFeedFav(FeedFavDto dto) {
        log.info("dto : {}", dto);
        return service.toggleFeedFav(dto);
    }

    // ifeed, iuser를 체크함으로써 feed를 삭제한다
    @DeleteMapping
    public ResVo delFeed(FeedDelDto dto) {
        log.info("delete result : {} ", dto);
        return service.delFeed(dto);
    }
}



// 작성시 pk(primary key = ifeed)는 요구하지 말것