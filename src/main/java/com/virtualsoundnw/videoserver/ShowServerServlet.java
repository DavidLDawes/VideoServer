package com.virtualsoundnw.videoserver;

import java.io.IOException;


import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.virtualsoundnw.videoserver.Name;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class ShowServerServlet extends HttpServlet {

	private static final String [] response0 = {
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\"",
		    "\"http://www.w3.org/TR/html4/strict.dtd\">",
			"<html lang=\"en\">",
			"<head>",
		    "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">",
		    "<title>Please Login</title>",
		    "</head>",
		    "<body>"
		};
		
		private static final String [] response1 = {
		"\">sign in</a>.</p>",
	    "</body>",
	    "</html>" };

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		
	    UserService userService = UserServiceFactory.getUserService();

	    String thisURL = req.getRequestURI();

	    resp.setContentType("text/html");
	    if (req.getUserPrincipal() != null) {
	    	resp.getWriter().println();

	    } else {
	    	for (String preR:response0) {
	    		resp.getWriter().println(preR);
	    	}
	        resp.getWriter().println( "<p>Please <a href=\"" +
	                                 userService.createLoginURL(thisURL) +
	                                 "\">sign in</a>.</p>");
	    	for (String postR:response1) {
	    		resp.getWriter().println(postR);
	    	}
	    }
    }	

}
