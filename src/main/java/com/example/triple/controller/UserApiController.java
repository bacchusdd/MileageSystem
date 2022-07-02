package com.example.triple.controller;

import com.example.triple.config.BasicResponse;
import com.example.triple.config.CommonResponse;
import com.example.triple.config.ErrorResponse;
import com.example.triple.dto.UserPointResponseDto;
import com.example.triple.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/users")
public class UserApiController {

    private final UserService userService;

    @ApiOperation(value="Inquiry of user point", notes="회원 ID로 포인트 조회")
    @GetMapping(value="/points", produces= MediaType.APPLICATION_JSON_VALUE)
    //public String getUserPoint(@RequestParam String userId){
    public BasicResponse getUserPoint(@ApiParam(value = "userId:UUID", required = true)
                                          @RequestParam String userId){

        UserPointResponseDto dto = userService.findUserPoint(userId);

        if (dto == null){
            return new ErrorResponse("없는 회원입니다. 포인트 조회 실패!");
        }
        //int points = dto.getPoints();
        //return Integer.toString(points);
        return new CommonResponse("POINT", "GET", dto);
    }
}
