package com.virtualsoundnw.videoserver;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.User;
import com.virtualsoundnw.videoserver.Name;

public class UserServerServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
    	resp.setContentType("text/plain");
		String Q = req.getQueryString();
		if (Q == null) {
			Debug.log("User servlet got no query\n");
			resp.getWriter().println( "ERROR" );
		} else {
			if (Q.startsWith("user=")) {
				String eMail = Q.substring(5);
				if (eMail.length() > 6) {
					try {
						Name myName = new Name(eMail, eMail);
						resp.getWriter().println("Add user with eMail=\""+eMail+"\"");
					} catch (Exception E) {
						resp.getWriter().println("ERROR eMail \""+eMail+"\" already in use.");				
					}
				}
			}
		}
    }	
}
