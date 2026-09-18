package com.V.FBasket.VnFBasket.service;

import com.V.FBasket.VnFBasket.model.Reviews;
import com.V.FBasket.VnFBasket.model.User;

import java.util.List;

public interface ReviewsService {

    Reviews  addReview(User user, Long productId, Long rating, String comment);
    List<Reviews> getReviewByProductId(Long productId);
    List<Reviews> getReviewByUserId(Long userId);
    Reviews updateReview(Long reviewId, Long userId, Long rating, String comment) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
}
