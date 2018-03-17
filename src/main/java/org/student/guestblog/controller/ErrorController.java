package org.student.guestblog.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Error controller.
 */
@RestController
public class ErrorController {

  /**
   * Handler for "/error" endpoint.
   *
   * @param request {@link HttpServletRequest} which is taken of this handler.
   * @return Map with error's description.
   */
  @RequestMapping(path = "/error")
  public Map<String, Object> handleError(HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();
    map.put("status", request.getAttribute("javax.servlet.error.status_code"));
    map.put("reason", request.getAttribute("javax.servlet.error.message"));
    return map;
  }
}
