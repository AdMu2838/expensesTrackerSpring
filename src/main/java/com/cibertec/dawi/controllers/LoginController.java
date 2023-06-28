package com.cibertec.dawi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cibertec.dawi.services.UsuarioService;

@Controller
public class LoginController {

    public LoginController() {
    	super();
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {
    	session.setAttribute(
	         "error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")
	      ); 
	    return "login"; 
    }
    
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key); 
        String error = ""; 
        if (exception instanceof BadCredentialsException) { 
           error = "Invalid username and password!";
           System.out.println(exception.getMessage());
           
        } else if (exception instanceof LockedException) { 
           error = exception.getMessage(); 
        } else { 
           error = "All is good!";
        } 
        System.out.println(error);
        return error;
     }
}