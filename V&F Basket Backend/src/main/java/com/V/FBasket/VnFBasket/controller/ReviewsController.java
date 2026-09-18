package com.V.FBasket.VnFBasket.controller;

import com.V.FBasket.VnFBasket.config.UserInfoUserDetails;
import com.V.FBasket.VnFBasket.dto.ReviewRequest;
import com.V.FBasket.VnFBasket.model.Reviews;
import com.V.FBasket.VnFBasket.model.User;
import com.V.FBasket.VnFBasket.serviceImpl.ReviewsServiceImpl;
import com.V.FBasket.VnFBasket.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/vnfbasket")
public class ReviewsController {

    @Autowired
    private ReviewsServiceImpl reviewsService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest reviewRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails userInfo = (UserInfoUserDetails) authentication.getPrincipal();
        User currentUser = userService.getUserById(userInfo.getUserId());
        Reviews review = reviewsService.addReview(currentUser, reviewRequest.getProductId(), reviewRequest.getRating(), reviewRequest.getComment());
        if(review!=null){
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getReviewsByProductId/{productId}")
    public ResponseEntity<List<Reviews>> getReviewsByProductId(@PathVariable Long productId) {
        List<Reviews> reviewsList = reviewsService.getReviewByProductId(productId);
        if(reviewsList!=null){
            return new ResponseEntity<>(reviewsList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getReviewsByUserId")
    public ResponseEntity<List<Reviews>> getReviewsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        List<Reviews> reviewsList = reviewsService.getReviewByUserId(userId);
        if (reviewsList != null) {
            return new ResponseEntity<>(reviewsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateReview/{reviewId}")
    public ResponseEntity<Reviews> updateReview(@RequestBody ReviewRequest reviewRequest, @PathVariable Long reviewId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        Reviews updateReview = reviewsService.updateReview(reviewId, userId, reviewRequest.getRating(), reviewRequest.getComment());
        if (updateReview != null) {
            return new ResponseEntity<>(updateReview, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user.getUserId();
        reviewsService.deleteReview(reviewId, userId);
        return new ResponseEntity<>("Review Deleted Successfully",HttpStatus.OK);

    }

}
