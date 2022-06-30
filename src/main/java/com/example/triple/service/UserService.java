package com.example.triple.service;

import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.ReviewRequestDto;
import com.example.triple.dto.UserPointResponseDto;
import com.example.triple.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    //find user-data by userId
    public UserResponseDto findUser (String userId){
        Users user = userRepository.findByUserId(userId);

        return new UserResponseDto(user);
    }

    //find user-point by userId
    public UserPointResponseDto findUserPoint (String userId){
        Users user = userRepository.findByUserId(userId);

        return new UserPointResponseDto(user);
    }


}
