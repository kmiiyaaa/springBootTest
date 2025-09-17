package com.kmii.project.reservation;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kmii.project.DataNotFoundException;
import com.kmii.project.user.SiteUser;
import com.kmii.project.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationService {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	UserService userService;
	
	
	// 모든 예약 리스트
	public Page<Reservation> getList(int page) {
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("rtime"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return reservationRepository.findAll(pageable);
	}
	
	
	 // 예약 생성
    public void create(SiteUser user, LocalDateTime rtime) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRtime(rtime);
        reservationRepository.save(reservation);
    }
    
    //id 맞는예약 가져오기
    public Reservation getReservation(Long id) {
    	
    	Optional<Reservation> rOptional = reservationRepository.findById(id);
    	
    	if(rOptional.isPresent()) {
    		return rOptional.get();
    	} else {
    		throw new DataNotFoundException(null);
    	}
    		
    }
    
    
    
    //예약 수정
    public void modify(Reservation reservation, SiteUser user, LocalDateTime rtime) {
    	
    	reservation.setUser(user);
    	reservation.setRtime(rtime);
    	reservationRepository.save(reservation);

    }
	
	//예약 삭제
    public void delete(Reservation reservation) {
    	reservationRepository.delete(reservation);
    }
	

}
