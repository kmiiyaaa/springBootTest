package com.kmii.project.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kmii.project.DataNotFoundException;
import com.kmii.project.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
private final BoardRepository boardRepository;
	
	// 모든 게시글 가져오기
	public Page<Board> getList(int page){  
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("bdate"));		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return boardRepository.findAll(pageable);
		
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
	public void create(String btitle, String bcontent, SiteUser user) {
		
		Board board = new Board();
		board.setBtitle(btitle);
		board.setBcontent(bcontent);
		board.setBdate(LocalDateTime.now());
		board.setAuthor(user);
		boardRepository.save(board);
		
	}
	
	// 게시글 수정
	public void modify(Board board, String btitle, String bcontent) {
		
		board.setBtitle(btitle);
		board.setBcontent(bcontent);
		board.setMdate(LocalDateTime.now());
		boardRepository.save(board);
		
	}
	
	//게시글 삭제
	public void delete(Board board) {
		boardRepository.delete(board);
	}
	
	
}
