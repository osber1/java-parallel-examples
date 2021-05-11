package com.osvaldas.learning.parallel.service;

import com.osvaldas.learning.parallel.domain.Review;

import static com.osvaldas.learning.parallel.util.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}
