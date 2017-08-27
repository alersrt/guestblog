package org.student.guestblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.guestblog.model.User;
import org.student.guestblog.service.SecurityService;
import org.student.guestblog.service.UserService;
import org.student.guestblog.validation.UserValidator;

import javax.validation.Valid;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") @RequestBody @Valid User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			LOGGER.info(bindingResult.toString());
			return "registration";
		}

		userService.addUser(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Username or password is incorrect.");
		}

		if (logout != null) {
			model.addAttribute("message", "Logged out successfully.");
		}

		return "login";
	}
}
