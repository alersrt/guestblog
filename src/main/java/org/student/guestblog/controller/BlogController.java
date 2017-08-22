package org.student.guestblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.student.guestblog.service.MessageService;

@Controller
public class BlogController {

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model) {
		model.addObject("listMessages", messageService.findAll());
		model.setViewName("main");
		return model;
	}
}
