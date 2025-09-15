package com.kmii.project.board;

import java.time.LocalDateTime;
import java.util.List;

import com.kmii.project.answer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="board") // 실제 매핑될 db 테이블 이름설정
public class Board {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bnum;
	
	@Column(length = 200)
	private String btitle;
	
	@Column(length = 500)
	private String bcontent;
	
	private Integer bhit = 0;
	
	private LocalDateTime bdate;  // 글 작성 시간
	
	// 글 : 댓글 = 1 : N
	@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
}
