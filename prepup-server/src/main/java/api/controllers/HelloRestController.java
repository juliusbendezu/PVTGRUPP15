package api.controllers;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/helloThere")
public class HelloRestController {

	@RequestMapping("/general")
	public String sayHello() {
		return "Hello There!";
	}
	
	@GetMapping("/specific")
	public String sayHello(@RequestParam(value = "name", defaultValue = "To You") String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
		return String.format("Hello There %s!", name);
	}
}
