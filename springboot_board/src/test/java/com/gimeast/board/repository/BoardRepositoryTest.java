package com.gimeast.board.repository;

import com.gimeast.board.entity.Board;
import com.gimeast.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 더미_데이터_생성() throws Exception {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .email("user"+i + "@gmail.com")
                    .build();

            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .member(member)
                    .build();

            boardRepository.save(board);
        });

    }

    @Test
    void 조회1() throws Exception {
        //given
        Optional<Board> result = boardRepository.findById(100L);

        //when
        Board board = result.get();

        //then
        System.out.println(board);
        System.out.println(board.getMember());

    }

    @Test
    void 조회_BoardWithMember() throws Exception {

        Object result = boardRepository.getBoardWithMember(100L);

        Object[] arr = (Object[]) result;
        System.out.println("========================");
        System.out.println(Arrays.toString(arr));
        System.out.println("========================");

    }

    @Test
    void 조회_BoardWithReply() throws Exception {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println("========================");
            System.out.println(Arrays.toString(arr));
            System.out.println("========================");
        }

    }

    @Test
    void 조회_BoardWithReplyCount() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] arr = row;
            System.out.println(Arrays.toString(arr));
        });

    }

    @Test
    void 상세조회() throws Exception {

        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));

    }

    @Test
    void testSearch1() throws Exception {
        boardRepository.search1();
    }

    @Test
    void searchPage() throws Exception {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.searchPage("", "", pageable);

    }

}