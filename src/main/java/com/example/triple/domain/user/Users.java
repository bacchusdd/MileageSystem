package com.example.triple.domain.user;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.pointhistory.Histories;
import com.example.triple.domain.review.Reviews;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "user", indexes = @Index(name = "idx_user", columnList = "userId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Users implements Serializable {

    @Id
    @Column(name="userId", length = 36)
    private String userId;


    @NotNull
    @Column(name = "points")
    @ColumnDefault("0")
    private int points;

    @Builder
    public Users(String userId, int points){
        this.userId = userId;
        this.points = points;
    }

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    @OneToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    private List<Histories> histories;

    public void increasePoint(int num){
        this.points += num;
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
