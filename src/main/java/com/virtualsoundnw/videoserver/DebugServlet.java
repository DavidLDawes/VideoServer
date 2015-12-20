package com.virtualsoundnw.videoserver;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

import com.virtualsoundnw.videoserver.Clip;
import com.virtualsoundnw.videoserver.Behavior;
import com.virtualsoundnw.videoserver.Name;
import com.virtualsoundnw.videoserver.Debug;

public class DebugServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		

		resp.setContentType("text/plain");

		if (Debug.debugEnabled)
		{
			resp.getWriter().println("Debug is enabled\n");
		} else {
			resp.getWriter().println("Debug is NOT enabled\n");
		}

		resp.getWriter().println("Debug log:\n" + Debug.getLog() +"\n\n");

		String Q = req.getQueryString();
		if (Q == null)
		{
			if (OState.objectify == null)
			{
				resp.getWriter().println("OState.objectify has not been intitialized, should've been done by the listener so this shouldn't happen.\n");
			} else {
				resp.getWriter().println("OState.objectify intitialized.\n");
				resp.getWriter().println("Dumping clips:\n");
				Query<Clip> q = OState.objectify.query(Clip.class);
				for (Clip allClips: q) {
					resp.getWriter().println(allClips.toString()+"\n");
				}
				
				resp.getWriter().println("Dumping users:\n");
				Query<Behavior> u = OState.objectify.query(Behavior.class);
				for (Behavior allUsers: u) {
					resp.getWriter().println(allUsers.toString()+"\n");
				}

				resp.getWriter().println("Dumping names:\n");
				Query<Name> n = OState.objectify.query(Name.class);
				for (Name allNames: n) {
					resp.getWriter().println(allNames.toString()+"\n");
				}
			
			}
		} else {
			if (Q.startsWith("debug=")|Q.startsWith("Debug=")|Q.startsWith("DEBUG=")) {
				Q = Q.substring(6);
				if (Q.startsWith("true")|Q.startsWith("1")|Q.startsWith("TRUE")|Q.startsWith("True")|Q.startsWith("255")|Q.startsWith("on")|Q.startsWith("On")|Q.startsWith("ON")|
					Q.startsWith(" true")|Q.startsWith(" 1")|Q.startsWith(" TRUE")|Q.startsWith(" True")|Q.startsWith(" 255")|Q.startsWith(" on")|Q.startsWith(" On")|Q.startsWith(" ON"))
				{
					Debug.debugEnabled = true;
				}
				if (Q.startsWith("false")|Q.startsWith("0")|Q.startsWith("FALSE")|Q.startsWith("False")|Q.startsWith("-1")|Q.startsWith("off")|Q.startsWith("Off")|Q.startsWith("OFF")|
						Q.startsWith(" false")|Q.startsWith(" 0")|Q.startsWith(" FALSE")|Q.startsWith(" False")|Q.startsWith(" -1")|Q.startsWith(" off")|Q.startsWith(" Off")|Q.startsWith(" OFF"))
				{
					Debug.debugEnabled = false;
				}
			}
		}
	}

}
