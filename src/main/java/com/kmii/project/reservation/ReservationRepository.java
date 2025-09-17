package com.kmii.project.reservation;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kmii.project.user.SiteUser;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findByUser(SiteUser user);
	Page<Reservation> findAll(Pageable pageable);

}
