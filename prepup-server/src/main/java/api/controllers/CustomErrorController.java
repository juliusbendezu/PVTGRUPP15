package api.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController{

	private static final String PATH = "/error";
	
	@GetMapping(value = PATH)
	public String error() {
		return "Custom error";
	}
	
	@Override
	public String getErrorPath() {
		return PATH;
	}

}
