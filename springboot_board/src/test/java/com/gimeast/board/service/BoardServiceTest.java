package com.gimeast.board.service;

import com.gimeast.board.dto.BoardDto;
import com.gimeast.board.dto.PageRequestDto;
import com.gimeast.board.dto.PageResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @Rollback(value = false)
    void 글등록() throws Exception {
        //given
        BoardDto dto = BoardDto.builder()
                .title("test title....")
                .content("content....")
                .memberEmail("user11@gmail.com")
                .build();

        //when
        Long bno = boardService.register(dto);

        //then
        System.out.println("bno : " + bno);
    }

    @Test
    void 목록조회() throws Exception {
        //given
        PageRequestDto pageRequestDto = new PageRequestDto();

        //when
        PageResultDto<BoardDto, Object[]> result = boardService.getList(pageRequestDto);

        //then
        for (BoardDto dto : result.getDtoList()) {
            System.out.println(dto);
        }

    }

    @Test
    void 상세조회() throws Exception {
        //given
        Long bno = 100L;

        //when
        BoardDto boardDto = boardService.get(bno);

        //then
        System.out.println(boardDto);

    }

    @Test
    @Rollback(value = false)
    void 삭제() throws Exception {
        //given
        Long bno = 1L;

        //when
        boardService.removeWithReplies(bno);

    }

    @Test
    @Rollback(value = false)
    void 수정() throws Exception {
        //given
        BoardDto boardDto = BoardDto.builder()
                .bno(2L)
                .title("제목 변경...")
                .content("내용 변경...")
                .build();

        //when
        boardService.modify(boardDto);

        //then

    }


}