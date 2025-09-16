package com.kmii.project.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmii.project.DataNotFoundException;
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
	
	// 댓글 조회 기능 - 답변 수정하려면 먼저 조회해야함
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = answerRepository.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	// 댓글 수정 
	public void modify(Answer answer, String acontent) {
		answer.setAcontent(acontent);
		answer.setMdate(LocalDateTime.now());
		answerRepository.save(answer);
	}
	
	
	// 댓글 삭제
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}
	
	// 댓글 추천
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		answerRepository.save(answer);
		
	}
	
	

}
