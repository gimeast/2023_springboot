package com.gimeast.mreview.service;

import com.gimeast.mreview.dto.ReviewDto;
import com.gimeast.mreview.entity.Review;
import com.gimeast.mreview.repository.ReviewRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDto> getReviewsOfMovie(Long mno) {
        List<Review> reviewList = reviewRepository.findByMovieMno2(mno);
        List<ReviewDto> result = reviewList.stream().map(review ->
                entityToDto(review)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Long register(ReviewDto reviewDto) {
        Review entity = dtoToEntity(reviewDto);
        Long reviewnum = reviewRepository.save(entity).getReviewnum();
        return reviewnum;
    }

    @Override
    public void modify(ReviewDto reviewDto) {
        Optional<Review> result = reviewRepository.findById(reviewDto.getReviewnum());

        if (result.isPresent()) {
            Review review = result.get();
            review.changeReview(reviewDto.getGrade(), reviewDto.getText());
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }

}
