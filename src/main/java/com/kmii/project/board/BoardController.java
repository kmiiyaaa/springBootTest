package com.kmii.project.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kmii.project.answer.AnswerForm;

import jakarta.validation.Valid;

@RequestMapping("/board")  //접두사
@Controller
public class BoardController {

	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value="/list")
	public String list(Model model) {  // 매개변수로 model 지정하면 객체가 자동으로 생성된다.
		
		List<Board> boardList = boardService.getList();
		model.addAttribute("boardList", boardList);
		
		return "board_list";
	}
	
	@GetMapping(value="/detail/{bnum}")
	public String detail(Model model, @PathVariable("bnum") Integer bnum, AnswerForm answerForm) {
		
		Board board = boardService.getBoard(bnum);
		model.addAttribute("board", board); 
		
		return "board_detail";		
	}
	
	@GetMapping(value="/create")
	public String boardCreate(BoardForm boardForm) {
		return "board_form";
	}
	
	@PostMapping(value="/create")
	public String boardCreate(@Valid BoardForm boardForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "board_form";
		}
		
		boardService.create(boardForm.getBtitle(), boardForm.getBcontent());
		
		return "redirect:/board/list";  // 게시글 작성 후 게시글 리스트로 이동
		
	}
	
}
