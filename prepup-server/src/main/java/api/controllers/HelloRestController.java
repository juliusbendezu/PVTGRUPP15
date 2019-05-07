package api.controllers;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/helloThere")
public class HelloRestController {

	@RequestMapping("/basic")
	public String sayHello() {
		return "Hello There!";
	}
	
	@RequestMapping(value = "name")
	public String sayHello(@RequestParam(value = "name", defaultValue = "You") String name) {
		return "Hello There " + name;
	}
}
