package org.shareio.backend.core.model;

import org.shareio.backend.core.model.vo.ReviewId;

import java.time.LocalDateTime;

public class Review {
    private ReviewId reviewId;
    private Offer offer;
    private Integer value; // 1-5 stars
    private LocalDateTime date;
}
