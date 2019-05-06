package com.example.demo;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/helloThere")
public class HelloRestController {

	@RequestMapping("/basic")
	public String sayHello() {
		return "Hello There!";
	}
	
	@RequestMapping(value = "/name")
	public String sayHello(@RequestParam(value = "name", required = true) String name) {
		return "Hello There " + name;
	}
}
