package com.kmii.project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		//비밀번호 확인 에러
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
		
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다");
			return "signup_form";	
		}
		
		try {
		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
		
		}catch(DataIntegrityViolationException e) {  // 중복 데이터 예외처리
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미 등록된 사용자ID 입니다.");
			return "signup_form";
		}catch(Exception e) {  // 나머지 예외처리
			e.printStackTrace();
			bindingResult.reject("signupFailed",e.getMessage());
			return "signup_form";	
		}
	
		return "redirect:/";
		
		}
	
	}
	

