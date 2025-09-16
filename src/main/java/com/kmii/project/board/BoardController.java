package com.kmii.project.board;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.kmii.project.answer.AnswerForm;
import com.kmii.project.user.SiteUser;
import com.kmii.project.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/board")  //접두사
@Controller
public class BoardController {

	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {  //첫페이지 = 0
		
		Page<Board> paging = boardService.getList(page);
		model.addAttribute("paging", paging);
		
		return "board_list";
	}
	
	@GetMapping(value="/detail/{bnum}")
	public String detail(Model model, @PathVariable("bnum") Integer bnum, AnswerForm answerForm) {
		
		Board board = boardService.getBoard(bnum);
		model.addAttribute("board", board); 
		
		return "board_detail";		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/create")
	public String boardCreate(BoardForm boardForm) {
		return "board_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/create")
	public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "board_form";
		}
		
		SiteUser siteUser = userService.getUser(principal.getName());
		boardService.create(boardForm.getBtitle(), boardForm.getBcontent(), siteUser);
		
		return "redirect:/board/list";  // 게시글 작성 후 게시글 리스트로 이동
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value="/modify/{bnum}")
	public String boardModify(BoardForm boardForm, @PathVariable("bnum") Integer bnum, Principal principal) {
		Board board = boardService.getBoard(bnum);
		if(!board.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
		}
		boardForm.setBtitle(board.getBtitle());
		boardForm.setBcontent(board.getBcontent());
		return "board_form";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/modify/{bnum}")
	public String boardModify(@Valid BoardForm boardForm, BindingResult bindingResult, Principal principal,
			@PathVariable("bnum") Integer bnum) {
		if(bindingResult.hasErrors()) {
			return "board_form";
		}
		Board board = boardService.getBoard(bnum);
		if(!board.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		boardService.modify(board, boardForm.getBtitle(), boardForm.getBcontent());
		return String.format("redirect:/board/detail/%s", bnum); // 수정하면 게시글상세페이지 리다이렉트
		
	}
}
