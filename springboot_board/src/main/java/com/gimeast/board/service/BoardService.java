package com.gimeast.board.service;

import com.gimeast.board.dto.BoardDto;
import com.gimeast.board.dto.PageRequestDto;
import com.gimeast.board.dto.PageResultDto;
import com.gimeast.board.entity.Board;
import com.gimeast.board.entity.Member;

public interface BoardService {

    /**
     * dto -> entity
     * @param dto
     * @return
     */
    default Board dtoToEntity(BoardDto dto) {

        Member member = Member.builder()
                .email(dto.getMemberEmail())
                .build();

        Board entity = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();

        return entity;
    }

    /**
     * entity -> dto
     * @param board
     * @param member
     * @param replyCount
     * @return
     */
    default BoardDto entityToDto(Board board, Member member, Long replyCount) {
        BoardDto boardDto = BoardDto.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .memberEmail(member.getEmail())
                .memberName(member.getName())
                .replyCount(replyCount)
                .build();

        return boardDto;
    }

    Long register(BoardDto boardDto);

    PageResultDto<BoardDto, Object[]> getList(PageRequestDto pageRequestDto);

    BoardDto get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDto boardDto);


}
