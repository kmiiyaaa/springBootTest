package com.kmii.project.reservation;


import java.time.LocalDate;
import java.time.LocalTime;

import com.kmii.project.user.SiteUser;
import com.sun.istack.NotNull;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReservationForm {
	
	
	private Long id;
	
	@Size(max=50)
	private String username;
	
	@NotNull
	@FutureOrPresent(message = "예약 날짜는 오늘 이후여야 합니다.")
	private LocalDate rdate;
	
	@NotNull
	private LocalTime rtime;

}
