package com.gimeast.mreview.service;

import com.gimeast.mreview.dto.ReviewDto;
import com.gimeast.mreview.entity.Member;
import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.entity.Review;

import java.util.List;

public interface ReviewService {

    default Review dtoToEntity(ReviewDto dto) {
        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .build();

        Member member = Member.builder()
                .mid(dto.getMid())
                .build();

        Review review = Review.builder()
                .reviewnum(dto.getReviewnum())
                .movie(movie)
                .member(member)
                .grade(dto.getGrade())
                .text(dto.getText())
                .build();

        return review;
    }

    default ReviewDto entityToDto(Review review) {
        ReviewDto dto = ReviewDto.builder()
                .reviewnum(review.getReviewnum())
                .mno(review.getReviewnum())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();

        return dto;
    }

    List<ReviewDto> getReviewsOfMovie(Long mno);

    Long register(ReviewDto reviewDto);

    void modify(ReviewDto reviewDto);

    void remove(Long reviewnum);

}
