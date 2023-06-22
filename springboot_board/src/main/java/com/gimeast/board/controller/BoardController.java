package com.gimeast.board.controller;

import com.gimeast.board.dto.BoardDto;
import com.gimeast.board.dto.PageRequestDto;
import com.gimeast.board.dto.PageResultDto;
import com.gimeast.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.model.IModel;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDto pageRequestDto, Model model) {
        PageResultDto<BoardDto, Object[]> list = boardService.getList(pageRequestDto);

        model.addAttribute("result", list);
    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String registerPost(BoardDto dto, RedirectAttributes redirectAttributes) {
        Long bno = boardService.register(dto);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public void read(@ModelAttribute PageRequestDto pageRequestDto,
                     @RequestParam(defaultValue = "R") String flag,
                     Long bno,
                     Model model) {

        BoardDto boardDto = boardService.get(bno);

        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("flag", flag);
        model.addAttribute("dto", boardDto);
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @PostMapping("modify")
    public String modify(@ModelAttribute BoardDto boardDto, PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes) {
        boardService.modify(boardDto);

        redirectAttributes.addAttribute("page", pageRequestDto.getPage());
        redirectAttributes.addAttribute("type", pageRequestDto.getType());
        redirectAttributes.addAttribute("keyword", pageRequestDto.getKeyword());
        redirectAttributes.addAttribute("bno", boardDto.getBno());

        return "redirect:/board/read";
    }


}
