package com.example.demo.service;
import java.util.Random;

public class service {
//	generate random value for cookies
	public String generateRandomValue(){
		Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 30) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, 30);
	}
	
}
