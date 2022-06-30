package com.example.triple.domain.review;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.ReviewRequestDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review", indexes = @Index(name = "idx_review", columnList = "reviewId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Reviews {

    @Id
    @Column(name = "reviewId")
    //@GeneratedValue(generator="uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String reviewId;

    @NotNull
    @Column(name = "content")
    private String content;

    //@NotNull
    //@Column(name = "userId")
    //private String userId;

    //@NotNull
    //@Column(name = "placeId")
    //private String placeId;

    @NotNull
    @Column(name = "point")
    private int point;

    //user 1 : review many
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId", columnDefinition="VARCHAR(36)")
    private Users users;

    //user 1 : review many
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "placeId", referencedColumnName = "placeId", columnDefinition="VARCHAR(36)")
    private Places places;


    /*
    @Builder
    public Reviews(String reviewId, String content, String userId, String placeId, int point){
        this.reviewId = reviewId;
        this.content = content;
        this.userId = userId;
        this.placeId = placeId;
        this.point = point;
    }

    public void update(String content, String userId, String placeId){
        this.content = content;
        this.userId = userId;
        this.placeId = placeId;
    }
*/
    @Builder
    public Reviews(String reviewId, String content, Users users, Places places, int point) {
        this.reviewId = reviewId;
        this.content = content;
        this.places = places;
        this.point = point;
        this.users = users;
    }

    /*
    public void update(String content, String placeId) {
        this.content = content;
        this.placeId = placeId;
    }
     */

    public void update(String content, Places places){
        this.content = content;
        this.places = places;
    }

    public void increasePoint(){
        this.point += 1;
    }

    public void decreasePoint(){
        if (point > 0) {
            this.point -= 1;
        }
    }

    public void initPoint(){
        this.point = 0;
    }


}
