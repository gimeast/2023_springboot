package com.gimeast.club.service;

import com.gimeast.club.entity.Note;
import com.gimeast.club.repository.NoteRepository;
import com.gimeast.club.dto.NoteDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Long register(NoteDto noteDto) {
        Note note = dtoToEntity(noteDto);
        noteRepository.save(note);
        return note.getNum();
    }

    @Override
    public NoteDto get(Long num) {
        Optional<Note> result = noteRepository.getWithWriter(num);
        if (result.isPresent()) {
            Note note = result.get();
            NoteDto noteDto = entityToDto(note);
            return noteDto;
        }
        return null;
    }

    @Override
    public void modify(NoteDto noteDto) {
        Optional<Note> result = noteRepository.findById(noteDto.getNum());
        if (result.isPresent()) {
            Note note = result.get();
            note.changeTitle(noteDto.getTitle());
            note.changeContent(note.getContent());
        }
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDto> getAllWithWriter(String writerEmail) {
        List<Note> list = noteRepository.getList(writerEmail);
        List<NoteDto> result = list.stream().map(note -> entityToDto(note)).collect(Collectors.toList());
        return result;
    }
}
