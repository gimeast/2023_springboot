package com.gimeast.mreview.repository;

import com.gimeast.mreview.entity.Member;
import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Rollback(value = false)
    void 리뷰등록() throws Exception {
        //given
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long) ((Math.random()*100)+1);
            Long mid = (long) ((Math.random()*100)+1);

            Member member = Member.builder()
                    .mid(mid)
                    .build();
            Movie movie = Movie.builder()
                    .mno(mno)
                    .build();

            Review review = Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade((int) ((Math.random()*5)+1))
                    .text("감상평...."+i)
                    .build();

            reviewRepository.save(review);
        });

        //when

        //then

    }

    @Test
    void 해당영화_리뷰조회() throws Exception {
        //given
        Long mno = 94L;
        //when
//        List<Review> reviews = reviewRepository.findByMovieMno(mno);
        List<Review> reviews = reviewRepository.findByMovieMno2(mno);

        //then
        reviews.forEach(review -> {
            System.out.println("==================");
            System.out.println(review.getGrade() + "\t" + review.getText() + "\t" + review.getMember().getEmail());
            System.out.println("==================");
        });

    }



}