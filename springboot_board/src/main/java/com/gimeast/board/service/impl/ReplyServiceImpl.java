package com.gimeast.board.service.impl;

import com.gimeast.board.dto.ReplyDto;
import com.gimeast.board.entity.Board;
import com.gimeast.board.entity.Reply;
import com.gimeast.board.repository.ReplyRepository;
import com.gimeast.board.service.ReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @Override
    public Long register(ReplyDto replyDto) {
        Reply reply = dtoToEntity(replyDto);
        Reply result = replyRepository.save(reply);
        return result.getRno();
    }

    @Override
    public List<ReplyDto> getList(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> replies = replyRepository.getRepliesByBoardOrderByRno(board);
        List<ReplyDto> replyDtoList = replies.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
        return replyDtoList;
    }

    @Override
    public void modify(ReplyDto replyDto) {
        Reply reply = dtoToEntity(replyDto);
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
