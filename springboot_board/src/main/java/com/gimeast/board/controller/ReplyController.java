package com.gimeast.board.controller;

import com.gimeast.board.dto.ReplyDto;
import com.gimeast.board.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/board/{bno}")
    public ResponseEntity<List<ReplyDto>> getReplies(@PathVariable Long bno) {

        List<ReplyDto> list = replyService.getList(bno);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping
    public ResponseEntity<Long> registReplies(@RequestBody ReplyDto replyDto) {
        Long result = replyService.register(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> delReplies(@PathVariable Long rno) {
        replyService.remove(rno);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modReplies(@RequestBody ReplyDto replyDto) {
        replyService.modify(replyDto);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }


}
