package com.gimeast.board.repository;

import com.gimeast.board.entity.Board;
import com.gimeast.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void 더미_데이터_생성() throws Exception {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder()
                    .bno(bno)
                    .build();

            Reply reply = Reply.builder()
                    .text("reply......" + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });

    }

    @Test
    void 조회1() throws Exception {
        //given
        Optional<Reply> result = replyRepository.findById(1L);

        //when
        Reply reply = result.get();

        //then
        System.out.println(reply);
        System.out.println(reply.getBoard());

    }
    
    @Test
    void 해당_댓글목록_조회() throws Exception {
        //given
        Board board = Board.builder()
                .bno(95L)
                .build();

        //when
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);

        //then
        replies.forEach(reply -> System.out.println(reply));
    }


}