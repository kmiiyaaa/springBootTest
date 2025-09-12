package com.kmii.project.answer;

import java.time.LocalDateTime;

import com.kmii.project.board.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

}
