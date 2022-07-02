package com.example.triple.controller;

import com.example.triple.config.BasicResponse;
import com.example.triple.config.CommonResponse;
import com.example.triple.config.ErrorResponse;
import com.example.triple.dto.HistoryResponseDto;
import com.example.triple.dto.UserPointResponseDto;
import com.example.triple.service.HistoryService;
import com.example.triple.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/pointhistories")
public class HistoryApiController {

    private final HistoryService historyService;
    private final UserService userService;


    @ApiOperation(value="Inquiry of user point history", notes="해당 user의 history list 조회")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResponse getUserHistories(@ApiParam(value = "userId:UUID", required = true)
                                              @RequestParam String userId){
        String msg;

        //없는 회원인 경우
        UserPointResponseDto userDto = userService.findUserPoint(userId);

        if (userDto == null){
            msg = "없는 회원입니다. history 조회 실패!";
            return new ErrorResponse(msg);
        }

        //이력 조회
        List<HistoryResponseDto> list = historyService.findUserHistory(userId);

        if (list.size() == 0) {
            //기록이 없는 경우
            msg = "아직 history가 없어요!";
            return new CommonResponse("USER HISTORY", "GET", msg);
        }
        else {
            return new CommonResponse("USER HISTORY", "GET", list);
        }
    }

    @ApiOperation(value="Inquiry of all users point history", notes="모든 user의 history list 최신순 조회")
    @GetMapping(value = "/all", produces= MediaType.APPLICATION_JSON_VALUE)
    public BasicResponse getAllHistories(@PageableDefault(size=5, sort="pointId", direction = Sort.Direction.DESC)
                                            Pageable pageable){

        String msg;

        List<HistoryResponseDto> list = historyService.findAllHistory(pageable);

        if (list.size() == 0) {
            //기록이 없는 경우
            msg = "=point history 있는 user가 한 명도 없어요!";
            return new CommonResponse("ALL HISTORY", "GET", msg);
        }
        else {
            return new CommonResponse("ALL HISTORY", "GET", list);
        }
    }

}
