package com.example.demo.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.demo.model.CookieModel;
import com.example.demo.model.User;
import com.example.demo.service.service;

@Controller
public class MainController {

	private service serviceObj = new service();
	HashMap<String,String> cookieStore = new HashMap<String,String>();  
	
	
	@GetMapping("/")
	public String home(HttpServletRequest request){
		return "login.html";
	}	
		
	@PostMapping("/usercredentials")
	public String submit(@ModelAttribute("User") User user, BindingResult result,
			HttpServletResponse response){

//		validate logins
		if(user.getUserName().equals("admin") && user.getUserPwd().equals("admin")){
//			random value for session Id
			String ssId = serviceObj.generateRandomValue();
			
			Cookie c1 = new Cookie("ssId",ssId);
			c1.setMaxAge(600*600); //1 hour (expire time)
			c1.setSecure(false);			
			response.addCookie(c1);
			
//			store session Id 
			cookieStore.put("ssId", ssId);
		
//			store csrf token (random seed)
			cookieStore.put("random_seed", serviceObj.generateRandomValue());			
			
//			redirect to the userPage
			return "redirect:userPage.html";
		}else
		
		return "redirect:errorPage.html";
	}
	
	@PostMapping("/getcsrftoken")
	public ResponseEntity<String> cookie(HttpServletResponse response, HttpServletRequest request){
		String x = cookieStore.get("random_seed");
		//get all cookies from request
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			 for (Cookie cookie : cookies){
				//validate the session cookie and send csrf 
				if (cookie.getValue().equals(cookieStore.get("ssId"))) {
					return ResponseEntity.status(HttpStatus.OK).body(x);
				}
			 }
		}
		return ResponseEntity.status(HttpStatus.OK).body("error");

	}
	
	@PostMapping("/userdetails")
	public ResponseEntity<String> userDetails(@ModelAttribute("CookieModel") CookieModel cModel, BindingResult result,
			HttpServletResponse response, HttpServletRequest request){
		
		//get all cookies from request
		Cookie[] cookies = request.getCookies();
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				//validate session cookie and csrf
				if (cookie.getValue().equals(cookieStore.get("ssId")) && cModel.getCsrfToken().equals(cookieStore.get("random_seed"))) {	
					return ResponseEntity.status(HttpStatus.OK).body("Transaction is success !!");
				}
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error in Transaction !!");
			
	}
	
	
}
