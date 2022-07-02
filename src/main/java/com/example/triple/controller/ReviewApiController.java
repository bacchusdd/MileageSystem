package com.example.triple.controller;

import com.example.triple.config.BasicResponse;
import com.example.triple.config.CommonResponse;
import com.example.triple.config.ErrorResponse;
import com.example.triple.dto.ReviewRequestDto;
import com.example.triple.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/events")
public class ReviewApiController {

    private final ReviewService reviewService;

    @ApiOperation(value="Processing review event", notes="ADD/MOD/DELETE 리뷰 이벤트 처리")
    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public BasicResponse reviewEvent(@ApiParam(value = "주어진 형식의 event", required = true)
                                         @RequestBody ReviewRequestDto dto){

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
