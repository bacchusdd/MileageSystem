package com.example.triple.domain.review;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.domain.user.Users;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review", indexes = @Index(name = "idx_review", columnList = "reviewId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Reviews {

    @Id
    @Column(name = "reviewId")
    private String reviewId;


    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "point")
    private int point;

    //user 1 : review many
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId", referencedColumnName = "userId", columnDefinition="VARCHAR(36)")
    private Users users;

    //user 1 : review many
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "placeId", referencedColumnName = "placeId", columnDefinition="VARCHAR(36)")
    private Places places;

    @OneToMany(mappedBy = "reviews", cascade = CascadeType.REMOVE)
    private List<Photos> photos;


    @Builder
    public Reviews(String reviewId, String content, Users users, Places places, int point) {
        this.reviewId = reviewId;
        this.content = content;
        this.places = places;
        this.point = point;
        this.users = users;
    }


    public void update(String content, Places places, int point) {
        this.content = content;
        this.places = places;
        this.point = point;
    }


    /*
    public void update(Reviews reviews){
        this.reviewId = reviews.getReviewId();
        this.content = reviews.getContent();
        this.places = reviews.getPlaces();
        this.point = reviews.getPoint();
        this.users = reviews.getUsers();
    }

     */

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

    public void setNull(){
        this.users = null;
        this.places = null;
    }


}
