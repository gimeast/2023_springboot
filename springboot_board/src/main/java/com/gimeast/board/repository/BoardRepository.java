package com.gimeast.board.repository;

import com.gimeast.board.entity.Board;
import com.gimeast.board.repository.querydsl.BoardRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    /**
     * 게시물 작성자 정보 가져오기
     * @param bno
     * @return
     */
    @Query("select b, m from Board b left join b.member m where b.bno = :bno")
    Object getBoardWithMember(@Param("bno") Long bno);

    /**
     * 게시물 댓글 가져오기
     * @param bno
     * @return
     */
    @Query("select b, r from Board b left join Reply r on b = r.board where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    /**
     * 목록화면 페이지 조회
     * @param pageable
     * @return
     */
    @Query(value = "select b, m, count(r) " +
            "from Board b " +
            "left join fetch b.member m " +
            "left join fetch Reply r " +
            "on b = r.board " +
            "group by b",
            countQuery = "select count(b) from Board b ")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    /**
     * 상세조회
     * @param bno
     * @return
     */
    @Query("select b, m, count(r) from Board b " +
            "left join fetch b.member m " +
            "left join fetch Reply r " +
            "on r.board = b " +
            "where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);


}
