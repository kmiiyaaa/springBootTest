package com.kmii.project.reservation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kmii.project.user.SiteUser;
import com.kmii.project.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    
	@Autowired 
    ReservationService reservationService;
	
	@Autowired
	private UserService userService;
    
    
    @GetMapping("/list")
    public String list(Model model) {
    	model.addAttribute("reservation", reservationService.getList());
    	return "reservation_list";
    }
    
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showReservationForm(Model model, Principal principal) {
        ReservationForm reservationForm = new ReservationForm();
        model.addAttribute("reservationForm", reservationForm);

        // 로그인한 사용자 이름 전달
        String username = principal.getName();
        model.addAttribute("username", username);

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


}
