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
		System.out.println("load "+request.getCookies());
		return "login.html";
	}
	
//	@PostMapping("/csrf")
//    public String GenerateCSRF(HttpServletResponse servletResponse){
//		
//		String csrfToken = serviceObj.generateRandomValue();
//        System.out.println("CSRF TOKEN : " + csrfToken);
//
//        return "{\"value\":\"csreToken\"}";
//    }
		
	@PostMapping("/usercredentials")
	public String submit(@ModelAttribute("User") User user, BindingResult result,
			HttpServletResponse response){
		
		if(user.getUserName().equals("admin") && user.getUserPwd().equals("admin")){
			
			String ssId = serviceObj.generateRandomValue();
			
			Cookie c1 = new Cookie("ssId",ssId);
			c1.setMaxAge(600*600); //1 hour
			c1.setSecure(true);			
			response.addCookie(c1);
			
			cookieStore.put("ssId", ssId);
		
			cookieStore.put("random_seed", serviceObj.generateRandomValue());			
			
			return "redirect:userPage.html";
		}else
		
		return "redirect:errorPage.html";
	}
	
	@PostMapping("/getcsrftoken")
	public ResponseEntity<String> cookie(HttpServletResponse response, HttpServletRequest request){
		String x = cookieStore.get("random_seed");
		return ResponseEntity.status(HttpStatus.OK).body(x);
	}
	
	@PostMapping("/userdetails")
	public ResponseEntity<String> userDetails(@ModelAttribute("CookieModel") CookieModel cModel, BindingResult result,
			HttpServletResponse response, HttpServletRequest request){
		
		//get all cookies from request
		Cookie[] cookies = request.getCookies();
		System.out.println(cookieStore.get("random_seed"));
		System.out.println("html "+cModel.getCsrfToken());
		System.out.println("ssId :"+ cookieStore.get("ssId"));
		System.out.println("test "+request.getCookies());
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				System.out.println("c : "+cookie);
				//check session cookie and csrf token
				if (cookie.getValue().equals(cookieStore.get("ssId")) && cModel.getCsrfToken().equals(cookieStore.get("random_seed"))) {
//					System.out.println(cookieStore.get("random_seed"));
					
					return ResponseEntity.status(HttpStatus.OK).body("Success CSRF TOKEN");
				}
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
			
//		if(cookieStore.get("random_seed").equals(cModel.getCsrfToken())){
//			return ResponseEntity.status(HttpStatus.OK).body("Success CSRF TOKEN");
//		}else{
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
//		}
		
//		return "redirect:login.html";
	}
	
	
}
