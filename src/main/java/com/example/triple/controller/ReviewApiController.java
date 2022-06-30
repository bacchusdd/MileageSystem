package com.example.triple.controller;

import com.example.triple.config.BasicResponse;
import com.example.triple.config.CommonResponse;
import com.example.triple.config.ErrorResponse;
import com.example.triple.dto.ReviewRequestDto;
import com.example.triple.service.PhotoService;
import com.example.triple.service.PlaceService;
import com.example.triple.service.ReviewService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/events")
public class ReviewApiController {

    private final ReviewService reviewService;
    private final PhotoService photoService;
    private final PlaceService placeService;

    @ApiOperation(value="리뷰 이벤트", notes="ADD/MOD/DELETE 리뷰 이벤트 처리")
    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    //@JsonProperty("requestDto")
    public BasicResponse reviewEvent(@RequestBody ReviewRequestDto dto){

        //System.out.println(dto.getType().trim());
        String msg;

        if(dto.getType().trim().equals("REVIEW")){

            if (dto.getAction().trim().equals("ADD")){
                //System.out.println("ADD");
                msg = reviewService.addReview(dto);
            }
            else if (dto.getAction().trim().equals("MOD")){
                msg = reviewService.modReview(dto);
            }
            else if (dto.getAction().trim().equals("DELETE")) {
                msg = reviewService.deleteReview(dto);
            }
            else{
                msg = "잘못된 action입니다!";
            }

            return new CommonResponse(dto.getType(), dto.getAction(), msg);
        }
        else{
            return new ErrorResponse("리뷰 이벤트가 아닙니다!");
        }
    }
}
