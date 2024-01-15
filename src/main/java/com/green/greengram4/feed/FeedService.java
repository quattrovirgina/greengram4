package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
   private final FeedMapper mapper;
   private final FeedPicsMapper picsMapper;
   private final FeedFavMapper favMapper;
   private final FeedCommentMapper commentMapper;
   private final AuthenticationFacade authenticationFacade;
   private final MyFileUtils myFileUtils;

   public ResVo postFeed(FeedInsDto dto) {

      dto.setIuser(authenticationFacade.getLoginUserPk());
      log.info("dto = {}",dto);
      int feedAffectedRows = mapper.insFeed(dto);
      log.info("feedAffectedRows: {}", feedAffectedRows);

      FeedPicsInsDto dto2 = new FeedPicsInsDto();
      dto2.setIfeed(dto.getIfeed());
      String target = "/feed/" + dto.getIfeed();

      for(MultipartFile file : dto.getPics()) {
         String saveFileNm = myFileUtils.transferTo(file, target);
         dto2.getPics().add(saveFileNm);

      }

      int feedPicsAffectedRows = picsMapper.insFeedPics(dto2);
      return new ResVo(dto.getIfeed());
   }

   public List<FeedSelVo> getFeedAll(FeedSelDto dto) {
      System.out.println("!!!!!");
      List<FeedSelVo> list = mapper.selFeedAll(dto);

      FeedCommentSelDto fcDto = new FeedCommentSelDto();
      fcDto.setStartIdx(0);
      fcDto.setRowCount(Const.FEED_COMMENT_FIRST_CNT);

      for(FeedSelVo vo : list) {
         List<String> pics = picsMapper.SelFeedPics(vo.getIfeed());
         vo.setPics(pics);

         fcDto.setIfeed(vo.getIfeed());
         List<FeedCommentSelVo> comments = commentMapper.selFeedCommentAll(fcDto);
         vo.setComments(comments);

         if(comments.size() == Const.FEED_COMMENT_FIRST_CNT) {
            vo.setIsMoreComment(1);
            comments.remove(comments.size() - 1);
         }
      }
      return list;
   }

   public ResVo delFeed(FeedDelDto dto) {
      //1 이미지
      int picsAffectedRows = picsMapper.DelFeedPics(dto);
      if(picsAffectedRows == 0) {
         return new ResVo(Const.FAIL);
      }

      //2 좋아요
      int favAffectedRows = favMapper.delFeedFavAllTest(dto);

      //3 댓글
      int commentAffectedRows = commentMapper.delFeedComment(dto);

      //4 피드
      int feedAffectedRows = mapper.DelFeed(dto);
      return new ResVo(Const.SUCCESS);
   }

   //--------------- t_feed_fav
   public ResVo toggleFeedFav(FeedFavDto dto) {
      //ResVo - result값은 삭제했을 시 (좋아요 취소) 0, 등록했을 시 (좋아요 처리) 1
      int delAffectedRows = favMapper.DelFav(dto);
      if(delAffectedRows == 1) {
         return new ResVo(Const.FEED_FAV_DEL);
      }
      int insAffectedRows = favMapper.insFeedFav(dto);
      return new ResVo(Const.FEED_FAV_ADD);
   }
}