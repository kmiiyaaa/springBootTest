package com.kmii.project.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmii.project.board.Board;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public void create(Board board, String acontent) {
		
		Answer answer = new Answer();
		answer.setAcontent(acontent);
		answer.setAdate(LocalDateTime.now());
		answer.setBoard(board);
		answerRepository.save(answer);
		
		
	}

}
