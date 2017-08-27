package org.student.guestblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.student.guestblog.model.Message;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.validation.MessageValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;

@Controller
public class MessageController {
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

	@RequestMapping(value = "/addMessage", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("messageForm", new Message());
		return "add";
	}

	@RequestMapping(value = "/addMessage", method = RequestMethod.POST)
	public String add(@ModelAttribute("messageForm")
						  @RequestBody
						  @Valid Message messageForm,
					  BindingResult bindingResult,
					  Model model) {
		messageValidator.validate(messageForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "add";
		}

		messageService.save(messageForm);
		return "redirect:/";
	}

	@RequestMapping(value = "/delMessage", method = RequestMethod.GET)
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
