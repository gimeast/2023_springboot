package com.gimeast.club.service;

import com.gimeast.club.entity.ClubMember;
import com.gimeast.club.entity.Note;
import com.gimeast.club.dto.NoteDto;

import java.util.List;

public interface NoteService {

    Long register(NoteDto noteDto);

    NoteDto get(Long num);

    void modify(NoteDto noteDto);

    void remove(Long num);

    List<NoteDto> getAllWithWriter(String writerEmail);

    default Note dtoToEntity(NoteDto noteDto) {
        ClubMember writer = ClubMember.builder().email(noteDto.getWriterEmail()).build();

        Note note = Note.builder()
                .num(noteDto.getNum())
                .title(noteDto.getTitle())
                .content(noteDto.getTitle())
                .writer(writer)
                .build();

        return note;
    }

    default NoteDto entityToDto(Note note) {
        NoteDto noteDto = NoteDto.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();

        return noteDto;
    }




}
