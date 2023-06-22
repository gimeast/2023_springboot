package com.gimeast.board.service;

import com.gimeast.board.dto.ReplyDto;
import com.gimeast.board.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Test
    void 댓글_목록_조회() throws Exception {
        //given
        Long bno = 95L;

        //when
        List<ReplyDto> list = replyService.getList(bno);

        //then
        list.stream().forEach(dto -> System.out.println(dto));
    }

}
