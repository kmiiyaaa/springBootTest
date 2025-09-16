package com.kmii.project.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/modify/{bnum}")
	public String answerModify(AnswerForm answerForm, Principal principal, @PathVariable("bnum") Integer bnum) {
		
		Answer answer = answerService.getAnswer(bnum);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		
		answerForm.setAcontent(answer.getAcontent());
		return "answer_form";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/modify/{bnum}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
			@PathVariable("bnum") Integer bnum, Principal principal){
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		
		Answer answer = answerService.getAnswer(bnum);
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
		}
		
		answerService.modify(answer, answerForm.getAcontent());
		return String.format("redirect:/board/detail/%s", answer.getBoard().getBnum());
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/delete/{bnum}")
	public String answerDelete(Principal principal, @PathVariable("bnum") Integer bnum) {
		
		Answer answer = answerService.getAnswer(bnum);
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다");
		}
		
		answerService.delete(answer);
		return String.format("redirect:/board/detail/%s", answer.getBoard().getBnum());
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/vote/{bnum}")
	public String answerVote(Principal principal, @PathVariable("bnum") Integer bnum) {
		
		Answer answer = answerService.getAnswer(bnum);
		SiteUser siteUser = userService.getUser(principal.getName());
		answerService.vote(answer, siteUser);
		return String.format("redirect:/board/detail/%s", answer.getBoard().getBnum());
		
	}
	
	
}
