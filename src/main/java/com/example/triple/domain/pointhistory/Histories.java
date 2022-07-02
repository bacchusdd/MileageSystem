package com.example.triple.domain.pointhistory;

import com.example.triple.domain.BaseTimeEntity;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.user.Users;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name="pointhistory")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Histories extends BaseTimeEntity {

    @Id
    @Column(name = "pointId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;


    /** msg : content/photo/bonus/delete **/
    @Column(name = "type")
    private String type;

    /** msg : increase/decrease + #point **/
    @Column(name = "action")
    private String action;

    /** 연관된 리뷰 ID **/
    @Column(name = "reviewId")
    private String reviewId;

    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    //1 user : many history
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId", referencedColumnName = "userId", columnDefinition="VARCHAR(36)")
    private Users users;

    @Builder
    public Histories(String type, String action, String reviewId, Users users, LocalDateTime createdDate){
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.users = users;
        this.createdDate = createdDate;
    }
}
