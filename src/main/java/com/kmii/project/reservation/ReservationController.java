package com.kmii.project.reservation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.kmii.project.user.SiteUser;
import com.kmii.project.user.UserService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/reservation")
public class ReservationController {
    
	@Autowired 
    ReservationService reservationService;
	
	@Autowired
	private UserService userService;
    
   
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showReservationForm(Model model, Principal principal) {
        ReservationForm reservationForm = new ReservationForm();
        model.addAttribute("reservationForm", reservationForm);

        // 로그인한 사용자 이름 전달
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("reservationForm", reservationForm);
        return "reservation_form"; // Thymeleaf 파일 이름
    }
    
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String reservationCreate(@Valid ReservationForm reservationForm, 
            BindingResult bindingResult,
            Principal principal) {

	if (bindingResult.hasErrors()) {
	return "reservation_form";
	}
	
	SiteUser siteUser = userService.getUser(principal.getName());
	
	// LocalDate + LocalTime -> LocalDateTime 합치기
	LocalDateTime reservationDateTime = reservationForm.getRdate()
	                                  .atTime(reservationForm.getRtime());
	
	reservationService.create(siteUser, reservationDateTime);
	
	return "redirect:/reservation/list";
	}
    
    
    // 예약 리스트 조회
    @GetMapping("/list")
    public String reservationList(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
    	
    	Page<Reservation> reservations = reservationService.getList(page);
    	
    	int totalPage = reservations.getTotalPages();
    	int currentPage = reservations.getNumber();
    	int groupSize = 5;
    	
    	//현재 그룹번호
    	int currentGroup = currentPage/groupSize;
    	
    	//그룹 시작, 끝
    	int startPage = currentGroup*groupSize;
    	int endPage = Math.min(startPage+groupSize-1,totalPage-1);
    	
    	//이전그룹, 다음그룹 여부
    	boolean hasPreGroup = startPage>0;
    	boolean hasNextGroup = endPage < totalPage-1;
        
    	
    	model.addAttribute("reservations", reservations);
    	model.addAttribute("startPage", startPage);
    	model.addAttribute("endPage", endPage);
    	model.addAttribute("hasPreGroup", hasPreGroup);
    	model.addAttribute("hasNextGroup", hasNextGroup);
        return "reservation_list";
    }
    
    //예약 수정폼 이동
    @PreAuthorize("isAuthenticated()")
	@GetMapping(value="/modify/{id}")
    public String showModifyForm(Model model, @PathVariable("id") Long id, Principal principal) {
        Reservation reservation = reservationService.getReservation(id);

        if (!reservation.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
        }

        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setId(reservation.getId()); // 여기서 id 담기
        reservationForm.setUsername(reservation.getUser().getUsername());
        reservationForm.setRdate(reservation.getRtime().toLocalDate());
        reservationForm.setRtime(reservation.getRtime().toLocalTime());

        model.addAttribute("reservationForm", reservationForm);
        model.addAttribute("username", reservation.getUser().getUsername());
        return "reservation_form";
    }

    
    @PreAuthorize("isAuthenticated()")
	@PostMapping(value="/modify/{id}")
    public String modifyReservation(@Valid ReservationForm reservationForm, BindingResult bindingResult,
            Principal principal) {

		if (bindingResult.hasErrors()) {
		return "reservation_form";
		}
		
		// id로 기존 예약 가져오기
		Reservation reservation = reservationService.getReservation(reservationForm.getId());
		
		if (!reservation.getUser().getUsername().equals(principal.getName())) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한 없음");
		}
		
		SiteUser user = userService.getUser(reservationForm.getUsername());
		LocalDateTime dateTime = LocalDateTime.of(reservationForm.getRdate(), reservationForm.getRtime());
		
		reservationService.modify(reservation, user, dateTime); // 기존 엔티티 수정
		
		return "redirect:/reservation/list";
		}
    
    
    @PreAuthorize("isAuthenticated()")
	@GetMapping(value="/delete/{id}")
    public String deleteReservation(Principal principal, @PathVariable("id") Long id) {
    	
    	Reservation reservation = reservationService.getReservation(id);
    	if(!reservation.getUser().getUsername().equals(principal.getName())) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다" );
    	}
    	
    	reservationService.delete(reservation);
    	return "redirect:/reservation/list";
    	
    	
    }

}
