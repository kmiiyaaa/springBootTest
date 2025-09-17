package com.kmii.project.reservation;


import java.time.LocalDate;
import java.time.LocalTime;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReservationForm {
	
	
	 private Long id;

	 private String username;

	 @NotNull(message = "예약 날짜를 입력하세요.")
	 private LocalDate rdate;

	 @NotNull(message = "예약 시간을 입력하세요.")
	 private LocalTime rtime;
}
