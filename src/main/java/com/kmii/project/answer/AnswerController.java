package com.kmii.project.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kmii.project.board.Board;
import com.kmii.project.board.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping(value="/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private AnswerService answerService;
	
	@PostMapping(value="/create/{bnum}")
	public String create(Model model,@PathVariable("bnum") Integer bnum ,@Valid AnswerForm answerForm, BindingResult bindingResult) {
		
		Board board = boardService.getBoard(bnum);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("board", board);
			return "board_detail";
		} 
		
		answerService.create(board, answerForm.getAcontent());
		return String.format("redirect:/board/detail/%s", bnum);
	}

}
