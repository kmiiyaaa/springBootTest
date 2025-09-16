package com.kmii.project.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.kmii.project.user.SiteUser;
import com.kmii.project.board.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name="answertbl")
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id; // 기본키, 자동증가
	
	@Column()
	private String acontent;
	
	private LocalDateTime adate;
	
	// 댓글 : 게시글 = N:1
	@ManyToOne
	private Board board;
	
	//답변들 : 작성자 -> N:1 ->  작성자 한명이 답변 여러개 쓸 수 있으니까
	@ManyToOne
	private SiteUser author;  // 글쓴이
		
	private LocalDateTime mdate;
	
	// 하나의 질문에 여러사람이 추천가능 , 한사람이 여러글 추천가능 -> N:N
	// Set으로 설정한 이유 -> voter 속성값이 서로 중복 되지 않도록 하기위해
	@ManyToMany 
	Set<SiteUser> voter;


}
