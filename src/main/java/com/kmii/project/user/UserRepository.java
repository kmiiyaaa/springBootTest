package com.kmii.project.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<SiteUser, Long> {
	
	//UserSecurityService - ID조회하는기능 필요, ID로 SiteUser 엔티티 조회하는 메서드 추가
	Optional<SiteUser> findByUsername(String username);

}
