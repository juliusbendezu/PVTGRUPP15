package com.example.demo;

import org.springframework.web.bind.annotation.*;



@RestController
public class HelloRestController {

	@RequestMapping("/Hellothere")
	public String sayHello() {
		return "Hello There!";
	}
}
