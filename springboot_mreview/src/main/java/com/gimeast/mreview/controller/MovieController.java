package com.gimeast.mreview.controller;

import com.gimeast.mreview.dto.MovieDto;
import com.gimeast.mreview.dto.PageRequestDto;
import com.gimeast.mreview.service.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/register")
    public void register() {}

    @PostMapping("/register")
    public String postRegister(MovieDto movieDto) {
        log.info("movieDto: " + movieDto);

        movieService.register(movieDto);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(@ModelAttribute PageRequestDto pageRequestDto, Model model) {
        log.info("pageRequestDto: " + pageRequestDto);
        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("result", movieService.getList(pageRequestDto));
    }

    @GetMapping("/read")
    public void read(@ModelAttribute PageRequestDto pageRequestDto, Model model, @RequestParam Long mno) {
        MovieDto movieDto = movieService.getMovie(mno);
        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("dto", movieDto);
    }

    @PostMapping("/remove")
    public ResponseEntity remove(@RequestBody MovieDto movieDto) {
        movieService.remove(movieDto.getMno());
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping("/modify")
    public String modify(@ModelAttribute PageRequestDto pageRequestDto, Model model, @RequestParam Long mno, @RequestParam String flag) {
        MovieDto movieDto = movieService.getMovie(mno);
        model.addAttribute("pageRequestDto", pageRequestDto);
        model.addAttribute("dto", movieDto);
        model.addAttribute("flag", flag);
        return "/movie/register";
    }

    @PostMapping("/modify")
    public String postModify(@ModelAttribute PageRequestDto pageRequestDto, @ModelAttribute MovieDto movieDto, RedirectAttributes redirectAttributes) {
        movieService.modify(movieDto);

        redirectAttributes.addAttribute("mno", movieDto.getMno());
        return "redirect:/movie/read";
    }


}
