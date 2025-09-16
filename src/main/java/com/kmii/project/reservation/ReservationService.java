package com.kmii.project.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public List<Reservation> getList() {
		
		return reservationRepository.findAll();	
	}
	
	
	 // 예약 생성
    public void create(SiteUser user, LocalDateTime rtime) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRtime(rtime);
        reservationRepository.save(reservation);
    }
	
	
	

}
