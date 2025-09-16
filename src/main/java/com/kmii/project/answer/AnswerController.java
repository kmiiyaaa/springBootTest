package com.kmii.project.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kmii.project.board.Board;
import com.kmii.project.board.BoardService;
import com.kmii.project.user.SiteUser;
import com.kmii.project.user.UserService;

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
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/create/{bnum}")
	public String create(Model model,@PathVariable("bnum") Integer bnum ,@Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
		
		Board board = boardService.getBoard(bnum);
		SiteUser siteUser = userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("board", board);
			return "board_detail";
		} 
		
		answerService.create(board, answerForm.getAcontent(),siteUser); //principal 객체를 통해 사용자명을 얻은 후 , siteUser 객체를 얻어 답변등록에 사용
		return String.format("redirect:/board/detail/%s", bnum);
	}
}
