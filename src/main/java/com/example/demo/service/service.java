package com.example.demo.service;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.example.demo.model.CookieModel;

public class service {

	public String generateRandomValue(){
		Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 30) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, 30);
	}
	
	public CookieModel getCookieDetails(HttpServletRequest servletRequest) {

        CookieModel model = new CookieModel();

        for (Cookie cookie : servletRequest.getCookies()) {
            if (cookie.getName().equals("JSESSIONID")) {
                model.setSessionId(cookie.getValue());
            }
            
            if (cookie.getName().equals("x-csrf-token")) {
                model.setCsrfToken(cookie.getValue());
            }
        }

        return model;
    }
}
