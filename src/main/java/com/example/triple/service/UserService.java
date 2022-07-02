package com.example.triple.service;

import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.UserPointResponseDto;
import com.example.triple.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    @Transactional(propagation = Propagation.REQUIRED)
    //find user-data by userId
    public UserResponseDto findUser (String userId){
        Users user = userRepository.findByUserId(userId);

        return new UserResponseDto(user);
    }


    @Transactional
    //find user-point by userId
    public UserPointResponseDto findUserPoint (String userId){
        Users user = userRepository.findByUserId(userId);

        return new UserPointResponseDto(user);
    }


}
