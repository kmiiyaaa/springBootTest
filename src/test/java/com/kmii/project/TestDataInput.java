package com.kmii.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kmii.project.board.BoardService;

@SpringBootTest
public class TestDataInput {

	@Autowired
	private BoardService boardService;
	
	@Test
	public void inputData() {
		for(int i=1; i<=300; i++) {
			String btitle = String.format("테스트 데이터:[%03d]", i);
			String bcontent = "내용 테스트 :)";
			boardService.create(btitle, bcontent);
		}
	}
}
