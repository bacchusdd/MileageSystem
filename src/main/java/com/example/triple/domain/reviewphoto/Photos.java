package com.example.triple.domain.reviewphoto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="reviewphoto")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Photos {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String attachedPhotoId;

    @Column(name = "reviewId")
    private String reviewId;

    @Builder
    public Photos(String attachedPhotoId, String reviewId){
        this.attachedPhotoId = attachedPhotoId;
        this.reviewId = reviewId;
    }
}
