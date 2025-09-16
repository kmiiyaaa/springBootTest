package com.kmii.project.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kmii.project.DataNotFoundException;
import com.kmii.project.answer.Answer;
import com.kmii.project.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final BoardRepository boardRepository;

	private final Specification<Board> search(String kw){
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true); // 중복 제거
				Join<Board, SiteUser> u1 = b.join("author", JoinType.LEFT);
				Join<Board, Answer> a = b.join("answerList", JoinType.LEFT);
				return cb.or(cb.like(b.get("btitle"), "%"+kw+"%"),  // 제목
						cb.like(b.get("bcontent"), "%"+kw+"%"), // 내용
						cb.like(u1.get("username"), "%"+kw+"%"), // 게시글 작성자
						cb.like(a.get("acontent"), "%"+kw+"%")); // 댓글 내용		
				
			}
		}; 
	}
	
	// 모든 게시글 가져오기
	public Page<Board> getList(int page, String kw){  
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("bdate"));		
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Specification<Board> spec = search(kw);
		return boardRepository.findAll(spec, pageable);
		
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
	
	// 추천
	public void vote(Board board, SiteUser siteUser) {
		board.getVoter().add(siteUser);
		boardRepository.save(board);  // 질문 엔티티에 추천인 저장
		
	}
	
	// 조회수 증가
	public void hit(Board board) {
		board.setBhit(board.getBhit()+1);
		boardRepository.save(board);
	}
	
	
	// 게시글 검색
	public void search() {
		
		
	}
	
	
}
