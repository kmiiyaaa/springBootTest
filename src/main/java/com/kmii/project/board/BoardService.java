package com.kmii.project.board;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kmii.project.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;
	
	public List<Board> getList(){  // 모든 게시글 가져오기
		
		return boardRepository.findAll();
		
	}
	
	public Board getBoard(Integer bnum) {
		
		Optional<Board> bOptional = boardRepository.findById(bnum);
		
		if(bOptional.isPresent()) {
			return bOptional.get();
		} else {
			throw new DataNotFoundException(null);
		}
	}

}
