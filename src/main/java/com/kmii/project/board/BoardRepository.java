package com.kmii.project.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	Board findByBtitle(String btitle);
	Board findByBtitleAndBcontent(String btitle, String content);
	List<Board> findByBtitleLike(String btitle);
	Page<Board> findAll(Pageable pageable);
	Page<Board> findAll(Specification<Board> spec, Pageable pageable); //  검색 조회 후 페이징 반환

}
