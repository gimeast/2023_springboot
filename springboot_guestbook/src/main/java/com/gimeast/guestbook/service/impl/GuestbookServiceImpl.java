package com.gimeast.guestbook.service.impl;

import com.gimeast.guestbook.data.dto.GuestbookDto;
import com.gimeast.guestbook.data.dto.PageRequestDto;
import com.gimeast.guestbook.data.dto.PageResultDto;
import com.gimeast.guestbook.data.dto.SearchStatus;
import com.gimeast.guestbook.data.entity.Guestbook;
import com.gimeast.guestbook.repository.GuestbookRepository;
import com.gimeast.guestbook.service.GuestbookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@Transactional
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public GuestbookServiceImpl(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    @Override
    public Long register(GuestbookDto guestbookDto) {

        Guestbook guestBook = dtoToEntity(guestbookDto);
        log.info("dto to entity : " + guestBook);

        guestbookRepository.save(guestBook);

        return guestBook.getGno();
    }

    @Override
    public PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto requestDto, SearchStatus status) {

        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
//        Page<Guestbook> result = guestbookRepository.findAll(pageable);
        Page<Guestbook> result =
                guestbookRepository.findBySearch(new SearchStatus(status.getType(), status.getKeyword()), pageable);

        Function<Guestbook, GuestbookDto> fn = (entity -> entityToDto(entity));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public GuestbookDto read(Long gno) {
        Optional<Guestbook> result = guestbookRepository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDto guestbookDto) {
        Optional<Guestbook> result = guestbookRepository.findById(guestbookDto.getGno());

        if (result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeGuestbook(guestbookDto.getTitle(), guestbookDto.getContent());
        }
    }


}
