package org.water.billing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserProfileController {

	@RequestMapping(value="/profile",method=RequestMethod.GET)
	public String userProfile() {
		
		return "/userprofile";
	}
	
}
