package com.kmii.project.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmii.project.board.Board;
import com.kmii.project.user.SiteUser;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public void create(Board board, String acontent, SiteUser author) {
		
		Answer answer = new Answer();
		answer.setAcontent(acontent);
		answer.setAdate(LocalDateTime.now());
		answer.setBoard(board);
		answer.setAuthor(author);
		answerRepository.save(answer);
		
		
	}

}
