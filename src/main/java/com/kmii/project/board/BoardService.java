package com.kmii.project.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kmii.project.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	// 모든 게시글 가져오기
	public List<Board> getList(){  
		
		return boardRepository.findAll();
		
	}
	
	// 게시글 번호 맞는 글 가져오기
	public Board getBoard(Integer bnum) {
		
		Optional<Board> bOptional = boardRepository.findById(bnum);
		
		if(bOptional.isPresent()) {
			return bOptional.get();
		} else {
			throw new DataNotFoundException(null);
		}
	}
	
	// 게시글 작성
	public void create(String btitle, String bcontent) {
		
		Board board = new Board();
		board.setBtitle(btitle);
		board.setBcontent(bcontent);
		board.setBdate(LocalDateTime.now());
		boardRepository.save(board);
		
	}

}
