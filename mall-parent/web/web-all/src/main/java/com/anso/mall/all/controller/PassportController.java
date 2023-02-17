package com.anso.mall.all.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-10 9:36
 */
@Controller
public class PassportController {
    /**
     * @return
     */
    @GetMapping("login.html")
    public String login(HttpServletRequest request) {
        String originUrl = request.getParameter("originUrl");
        request.setAttribute("originUrl", originUrl);
        return "login";
    }
}
