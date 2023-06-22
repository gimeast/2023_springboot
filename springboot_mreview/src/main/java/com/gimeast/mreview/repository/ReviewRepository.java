package com.gimeast.mreview.repository;

import com.gimeast.mreview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //방법1
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovieMno(Long mno);

    //방법2
    @Query("select r, m_member " +
            "from Review r " +
            "left join Member m_member " +
            "on r.member = m_member " +
            "left join Movie m " +
            "on m = r.movie " +
            "where m.mno = :mno")
    List<Review> findByMovieMno2(@Param("mno") Long mno);

    @Modifying
    @Query("delete from Review r where r.member.mid = :mid")
    void deleteByMemberMid(@Param("mid") Long mid);

    void deleteByMovieMno(Long mno);





}
