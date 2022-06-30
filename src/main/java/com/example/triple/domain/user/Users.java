package com.example.triple.domain.user;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.review.Reviews;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user", indexes = @Index(name = "idx_userId", columnList = "userId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Users {

    @Id
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String userId;

    @NotNull
    @Column(name = "points")
    private int points;

    @Builder
    public Users(String userId, int points){
        this.userId = userId;
        this.points = points;
    }

    public void increasePoint(){
        this.points += 1;
    }

    public void decreasePoint(int num){
        if (points > num) {
            this.points -= num;
        }
        else{
            this.points = 0;
        }
    }

    public void initPoint(int num){
        this.points = num;
    }

}
