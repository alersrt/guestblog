package org.student.guestblog.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HelloController {

  @GetMapping(value = "/")
  public String handleHello(HttpServletRequest request) {
    log.info("Hello, logger!");

    return "hello";
  }
}
