package com.kmii.project.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Entity
	@Table(name = "siteuser") //실제로 매핑될 데이터베이스의 테이블 이름 설정
	public class SiteUser {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;  // 유저번호, 기본키
		
		@Column( unique = true) //중복불가
		private String username;  //유저 아이디
		
		private String password;  // 유저 비밀번호
		
		@Column(name="email" , unique = true)// 중복불가
		private String email; // 유저 이메일


}
