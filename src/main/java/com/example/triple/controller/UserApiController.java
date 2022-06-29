package com.example.triple.controller;

import com.example.triple.config.BasicResponse;
import com.example.triple.config.CommonResponse;
import com.example.triple.config.ErrorResponse;
import com.example.triple.dto.UserResponseDto;
import com.example.triple.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/events")
public class UserApiController {

    private final UserService userService;

    @ApiOperation(value="회원 포인트 조회", notes="회원 ID로 포인트 조회")
    @GetMapping(value="/points", produces= MediaType.APPLICATION_JSON_VALUE)
    //public String getUserPoint(@RequestParam String userId){
    public BasicResponse getUserPoint(@RequestParam String userId){

        UserResponseDto dto = userService.findUser(userId);

        if (dto == null){
            return new ErrorResponse("포인트 조회 실패!");
        }
        int points = dto.getPoints();

        //return Integer.toString(points);
        return new CommonResponse("POINT", "GET", Integer.toString(points));
    }
}
