package com.procorp.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-service")
public class ResourceController {
	@RequestMapping("/isAuthenticated")
	public boolean isAuthenticated(){return true;}
}
