package com.gimeast.board.service;

import com.gimeast.board.dto.ReplyDto;
import com.gimeast.board.entity.Board;
import com.gimeast.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    default ReplyDto entityToDto(Reply dto) {
        ReplyDto replyDto = ReplyDto.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .regDate(dto.getRegDate())
                .modDate(dto.getModDate())
                .build();
        return replyDto;
    }

    default Reply dtoToEntity(ReplyDto entity) {
        Board board = Board.builder()
                .bno(entity.getBno())
                .build();

        Reply reply = Reply.builder()
                .rno(entity.getRno())
                .replyer(entity.getReplyer())
                .board(board)
                .text(entity.getText())
                .build();

        return reply;
    }

    Long register(ReplyDto replyDto);
    List<ReplyDto> getList(Long bno);
    void modify(ReplyDto replyDto);
    void remove(Long rno);

}
