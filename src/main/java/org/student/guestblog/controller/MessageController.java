package org.student.guestblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.method.P;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.student.guestblog.model.Message;
import org.student.guestblog.service.MessageService;
import org.student.guestblog.service.SecurityService;
import org.student.guestblog.service.UserService;
import org.student.guestblog.validation.MessageValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.format.DateTimeFormatter;

@Controller
public class MessageController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageValidator messageValidator;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(ModelAndView model) {
		model.addObject("listMessages", messageService.findAll());
		model.addObject("timeFormatter", DateTimeFormatter.ofPattern("KK:mm a"));
		model.addObject("dateFormatter", DateTimeFormatter.ofPattern("d MMM uuuu"));
		model.setViewName("main");
		return model;
	}

	@RequestMapping(value = "/addMessage", method = RequestMethod.GET)
	public String addMessage(Model model) {
		model.addAttribute("messageForm", new Message());
		return "add";
	}

	@RequestMapping(value = "/addMessage", method = RequestMethod.POST)
	public String addMessage(@ModelAttribute("messageForm")
					  @RequestBody
					  @Valid Message messageForm,
					  BindingResult bindingResult,
					  Model model) {
		messageValidator.validate(messageForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "add";
		}

		messageService.addMessage(messageForm, userService.findByUsername(securityService.findLoggedInUsername()));
		return "redirect:/";
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
//	@PreAuthorize("hasRole('ROLE_ADMIN') or messageServiceImpl.findById(#request.getParameter(\"id\")).user.username == authentication.name")
	@RequestMapping(value = "/delMessage", method = RequestMethod.GET)
	public String delete(@P("request") HttpServletRequest request) {
		String messageId = request.getParameter("id");
		if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
				messageService.findById(messageId).getUser().getUsername().equals(securityService.findLoggedInUsername())) {
			messageService.deleteById(messageId);
		} else LOGGER.error("The message is belong to another user");

		return "redirect:/";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws ServletException {
		// Convert multipart object to byte[]
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

	}
}
