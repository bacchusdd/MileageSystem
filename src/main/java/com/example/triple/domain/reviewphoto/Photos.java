package com.example.triple.domain.reviewphoto;

import com.example.triple.domain.review.Reviews;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name="reviewphoto")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Photos {

    @Id
    @Column(name = "attachedPhotoId", length = 36)
    private String attachedPhotoId;


    //review 1 : photo many
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reviewId_photo"),
            name = "reviewId", referencedColumnName = "reviewId", columnDefinition="VARCHAR(36)")
    private Reviews reviews;

    @Builder
    public Photos(String attachedPhotoId, Reviews reviews){
        this.attachedPhotoId = attachedPhotoId;
        this.reviews = reviews;
    }


}
