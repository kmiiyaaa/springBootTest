package com.kmii.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kmii.project.board.Board;
import com.kmii.project.board.BoardRepository;

@SpringBootTest
public class Test01 {

	@Autowired
	BoardRepository boardRepository;
	
//	@Test
//	@DisplayName("board 테이블에 게시글 저장하기")
//	public void testJpa1() {
//		Board b1 = new Board();
//		b1.setBtitle("test1"); //글제목
//		b1.setBcontent("테스트 중입니다"); // 글내용
//		b1.setBdate(LocalDateTime.now()); // 현재 시간 넣기
//		//b1 -> entity 생성 완료
//		boardRepository.save(b1);
//		
//		Board b2 = new Board();
//		b2.setBtitle("test2"); // 글제목
//		b2.setBcontent("안녕하세요~~"); // 글내용
//		b2.setBdate(LocalDateTime.now()); // 현재 시간 넣기
//		boardRepository.save(b2);
//	}

	@Test
	@DisplayName("모든 질문글 조회 테스트")
	public void tsetJpa() {
		Optional<Board> allBoard = boardRepository.findById(1);
		
		if(allBoard.isPresent()) {
			Board board = allBoard.get();
			assertEquals("test1", board.getBtitle());
		}
		
	}
	
}
