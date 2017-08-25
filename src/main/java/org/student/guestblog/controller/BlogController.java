package org.student.guestblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.student.guestblog.model.Message;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.validation.MessageValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

@Controller
public class BlogController {
	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageValidator messageValidator;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(ModelAndView model) {
		model.addObject("listMessages", messageService.findAll());
		model.addObject("timeFormatter", DateTimeFormatter.ofPattern("KK:mm a"));
		model.addObject("dateFormatter", DateTimeFormatter.ofPattern("d MMM uuuu"));
		model.setViewName("main");
		return model;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("messageForm", new Message());
		return "add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("messageForm") Message messageForm, BindingResult bindingResult, Model model) {
		messageValidator.validate(messageForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "add";
		}

		messageService.save(messageForm);
		return "redirect:/";
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String delete(HttpServletRequest request) {
		messageService.deleteById(request.getParameter("id"));
		return "redirect:/";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws ServletException {
		// Convert multipart object to byte[]
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

	}
}
