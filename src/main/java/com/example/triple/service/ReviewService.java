package com.example.triple.service;

import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;
    private final PlaceService placeService;
    private final PhotoService photoService;
    private final HistoryService historyService;


    @Transactional
    public String addReview(ReviewRequestDto dto){
        String msg = null;

        UserResponseDto userDto = userService.findUser(dto.getUserId());
        Users users = userDto.toEntity();

        Reviews reviews = dto.toEntityReviews(users.getPoints(), 0);

        //해당 place에 review 작성 이력 있는지 확인
        if (reviewRepository.existsByUsers_userIdAndPlaces_placeId(users.getUserId(), dto.getPlaceId()) == false) {

            /** 첫리뷰 bonus point 처리 **/
            //해당 placeId가 존재하지 않음
            if (placeService.existCheck(dto.getPlaceId()) == false){
                //장소 저장
                PlaceSaveReqeustDto placeDto = new PlaceSaveReqeustDto(reviews.getPlaces());
                placeService.save(placeDto);

                //리뷰에 보너스 점수 부여
                reviews.increasePoint();

                //history 저장
                //Histories histories = new Histories("bonus", "increase1", reviews.getReviewId(), users);
                //HistoryRequestDto historyDto = new HistoryRequestDto(histories);
                Users temp = users;
                HistorySaveRequestDto historyDto = new HistorySaveRequestDto("bonus", "increase1", reviews.getReviewId(),
                                                        temp.getUserId(), temp.getPoints());
                historyService.save(historyDto);

            }
            else {
                //해당 placeId가 존재하지만 리뷰는 없는 경우
                if (reviewRepository.countByPlaces_placeId(dto.getPlaceId()) == 0){
                    //보너스 점수 부여
                    reviews.increasePoint();

                    //history 저장
                    Users temp = users;
                    HistorySaveRequestDto historyDto = new HistorySaveRequestDto("bonus", "increase1", reviews.getReviewId(),
                                                    temp.getUserId(), temp.getPoints());
                    historyService.save(historyDto);
                }
            }

            /** 작성 point 처리 **/
            //내용 작성시
            if (dto.getContent() != null) {
                reviews.increasePoint();

                //history 저장
                Users temp = users;
                HistorySaveRequestDto historyDto = new HistorySaveRequestDto("content", "increase1", reviews.getReviewId(),
                                                temp.getUserId(), temp.getPoints());
                historyService.save(historyDto);
            }

            //사진 추가시
            if (dto.getAttachedPhotoIds().size() > 0){
                System.out.println("size + " + Integer.toString(dto.getAttachedPhotoIds().size()));
                reviews.increasePoint();

                //history 저장
                Users temp = users;
                HistorySaveRequestDto historyDto = new HistorySaveRequestDto("photo", "increase1", reviews.getReviewId(),
                                                temp.getUserId(), temp.getPoints());
                historyService.save(historyDto);
            }


            /** 해당 review point user에 적용 **/
            reviews.getUsers().increasePoint(reviews.getPoint());
            //users.increasePoint(reviews.getPoint());


            /** data 추가 **/
            reviewRepository.save(reviews);

            List<PhotoSaveRequestDto> photoDto = new ArrayList<>();
            for (Photos p : dto.toEntityPhotoList(reviews)){
                photoDto.add(new PhotoSaveRequestDto(p));
            }
            photoService.saveAll(photoDto);
            //photoService.saveAll(dto.toEntityPhotoList(reviews));

        }
        else{
            msg = "이미 review를 작성한 place예요!";
            return msg;
        }

        msg = "ADD 성공";
        return msg;
    }

    @Transactional
    public String modReview(ReviewRequestDto dto){
        String msg = null;

        //해당 reviewId가 존재하는지
        if (reviewRepository.existsByReviewId(dto.getReviewId()) == true){

            UserResponseDto userDto = userService.findUser(dto.getUserId());
            Users users = userDto.toEntity();

            Reviews reviews = reviewRepository.findByReviewId(dto.getReviewId());
            int pastpoint = reviews.getPoint();

            reviews = dto.toEntityReviews(users.getPoints(), pastpoint);

            /** point 처리 **/
            //이전에 사진 있었으나 사진 아예 삭제한 경우
            //if (photoService.checkReview(dto.getReviewId()) == true && dto.getAttachedPhotoIds().size() < 1){
            if (dto.getAttachedPhotoIds().size() < 1){
                if (photoService.checkReview(dto.getReviewId()) == true){

                    //리뷰 point, 유저 point 모두 -1
                    reviews.decreasePoint();

                    //history 저장
                    Users temp = users;
                    HistorySaveRequestDto historyDto = new HistorySaveRequestDto("photo", "decrease1", reviews.getReviewId(),
                            temp.getUserId(), temp.getPoints());
                    historyService.save(historyDto);

                    //기존 사진 삭제
                    photoService.deletePhotos(dto.getReviewId());
                }
            }
            else{
                if (photoService.checkReview(dto.getReviewId()) == false){
                    //리뷰 point, 유저 point 모두 +1
                    reviews.increasePoint();

                    //history 저장
                    Users temp = users;
                    HistorySaveRequestDto historyDto = new HistorySaveRequestDto("photo", "increase1", reviews.getReviewId(),
                            temp.getUserId(), temp.getPoints());
                    historyService.save(historyDto);
                }
            }

            //기존에 사진 없는 경우에 사진 추가시 +1
            //if (photoService.checkReview(dto.getReviewId()) == false && dto.getAttachedPhotoIds().size() > 0){
            //}

            /** 첫리뷰 bonus point 처리 **/
            //기존에 보너스 받지 않음
            if (historyService.checkType(dto.getReviewId(), "bonus") == false){
                //해당 placeId가 존재하지 않음
                if (placeService.existCheck(dto.getPlaceId()) == false){
                    //장소 저장
                    PlaceSaveReqeustDto placeDto = new PlaceSaveReqeustDto(reviews.getPlaces());
                    placeService.save(placeDto);

                    //리뷰에 보너스 점수 부여
                    reviews.increasePoint();

                    //history 저장
                    Users temp = users;
                    HistorySaveRequestDto historyDto = new HistorySaveRequestDto("bonus", "increase1", reviews.getReviewId(),
                                                    temp.getUserId(), temp.getPoints());
                    historyService.save(historyDto);

                }
                else {
                    //해당 placeId가 존재하지만 리뷰는 없는 경우
                    if (reviewRepository.countByPlaces_placeId(dto.getPlaceId()) == 0){
                        //보너스 점수 부여
                        reviews.increasePoint();

                        //history 저장
                        Users temp = users;
                        HistorySaveRequestDto historyDto = new HistorySaveRequestDto("photo", "increase1", reviews.getReviewId(),
                                                        temp.getUserId(), temp.getPoints());
                        historyService.save(historyDto);
                    }
                }
            }

            /** 해당 review point user에 적용 **/
            reviews.getUsers().increasePoint(reviews.getPoint());
            reviews.getUsers().decreasePoint(pastpoint);


            /** 데이터 처리 : 수정 **/
            reviewRepository.save(reviews);

            List<PhotoSaveRequestDto> photoDto = new ArrayList<>();
            for (Photos p : dto.toEntityPhotoList(reviews)){
                photoDto.add(new PhotoSaveRequestDto(p));
            }
            photoService.saveAll(photoDto);
            //photoService.saveAll(dto.toEntityPhotoList(reviews));


        }
        else{
            msg = "존재하지 않는 review예요!";
            return msg;
        }



        msg = "MOD 성공";
        return msg;
    }

    @Transactional
    public String deleteReview(ReviewRequestDto dto){
        String msg = null;

        //해당 reviewId가 존재하는지
        if (reviewRepository.existsByReviewId(dto.getReviewId()) == true){

            UserResponseDto userDto = userService.findUser(dto.getUserId());
            Users users = userDto.toEntity();

            Reviews pastReviews = reviewRepository.findByReviewId(dto.getReviewId());
            Reviews reviews = dto.toEntityReviews(users.getPoints(), pastReviews.getPoint());

            /** user point 처리 **/
            //reviews.getUsers().decreasePoint(pastReviews.getPoint());
            users.decreasePoint(pastReviews.getPoint());

            /** pointhistory 처리 **/
            //history 저장
            Users temp = users;
            HistorySaveRequestDto historyDto = new HistorySaveRequestDto("delete", "decrease" + Integer.toString(pastReviews.getPoint()), reviews.getReviewId(),
                                            temp.getUserId(), temp.getPoints());
            historyService.save(historyDto);


            /** data 처리 : photos & review 삭제 */
            //if (photoService.checkReview(dto.getReviewId()) == true) {
            //    photoService.deletePhotos(dto.getReviewId());
            //}

            reviewRepository.deleteByReviewId(dto.getReviewId());

        }
        else{
            msg = "존재하지 않는 review예요!";
            return msg;
        }

        msg = "DELETE 성공";
        return msg;
    }

}
