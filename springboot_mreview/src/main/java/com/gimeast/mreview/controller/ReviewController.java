package com.gimeast.mreview.controller;

import com.gimeast.mreview.dto.ReviewDto;
import com.gimeast.mreview.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDto>> getListReview(@PathVariable Long mno) {

        log.info("==================================");
        List<ReviewDto> reviews = reviewService.getReviewsOfMovie(mno);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> registerReview(@RequestBody ReviewDto reviewDto) {
        Long reviewnum = reviewService.register(reviewDto);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@RequestBody ReviewDto reviewDto) {
        reviewService.modify(reviewDto);
        return new ResponseEntity<>(reviewDto.getReviewnum(), HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> deleteReview(@PathVariable Long reviewnum) {
        reviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

}
