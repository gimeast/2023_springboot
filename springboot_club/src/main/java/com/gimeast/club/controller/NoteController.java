package com.gimeast.club.controller;

import com.gimeast.club.dto.NoteDto;
import com.gimeast.club.service.NoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@Log4j2
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody NoteDto noteDto) {
        log.info("----------------register-----------------");
        Long num = noteService.register(noteDto);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    @GetMapping("/{num}")
    public ResponseEntity<NoteDto> read(@PathVariable Long num) {
        log.info("----------------read-----------------");
        NoteDto noteDto = noteService.get(num);
        return new ResponseEntity<>(noteDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NoteDto>> getList(@RequestParam String email) {
        log.info("----------------getList-----------------");
        List<NoteDto> noteDtoList = noteService.getAllWithWriter(email);
        return new ResponseEntity<>(noteDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<String> remove(@PathVariable Long num) {
        log.info("----------------remove-----------------");
        noteService.remove(num);
        return new ResponseEntity<>("removed", HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<String> modify(@RequestBody NoteDto noteDto) {
        noteService.modify(noteDto);
        return new ResponseEntity<>("modified", HttpStatus.OK);
    }

}
