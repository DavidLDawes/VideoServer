package com.virtualsoundnw.videoserver;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.virtualsoundnw.videoserver.Behavior;

@SuppressWarnings("serial")
public class VideoServerServlet extends HttpServlet {

	public static String [] showTitles = new String[5];
	public static String [] actionsShow1= null;
	public static String [] actionsShow2= null;
	public static String [] actionsShow3= null;
	public static String [] actionsShow4= null;
	public static String [] actionsShow5= null;
	

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

    	resp.setContentType("text/plain");
		String Q = req.getQueryString();
		if (Q == null) {
			Debug.log("Video servlet got no query\n");
			resp.getWriter().println( "ERROR" );
		} else {
			if (Q.startsWith("listNewSongs,")) {
				for (String title:showTitles) { resp.getWriter().println( title ); }
				resp.getWriter().println();

				for (String s1:show1()) { resp.getWriter().println( s1 ); }
				resp.getWriter().println();

				for (String s2:show2()) { resp.getWriter().println( s2 ); }
				resp.getWriter().println();
				
				for (String s3:show3()) { resp.getWriter().println( s3 ); }
				resp.getWriter().println();
				
				for (String s4:show4()) { resp.getWriter().println( s4 ); }
				resp.getWriter().println();
				
				for (String s5:show5()) { resp.getWriter().println( s5 ); }
				resp.getWriter().println();
				
				String upLevel = Q.substring(13);
				int upgradeLevel = Integer.parseInt( upLevel );
				switch (upgradeLevel) {
				case 0:
					Debug.log("Upgrading from 0\n");
					resp.getWriter().println(formatArray(VideoListener.updateValues0));
					resp.getWriter().println(formatArray(VideoListener.updateBlog0));
					resp.getWriter().println("2\n");
   					break;
				case 1:
					Debug.log("Upgrading from 1\n");
					resp.getWriter().println(formatArray(VideoListener.updateValues1));
					resp.getWriter().println(formatArray(VideoListener.updateBlog1));
					resp.getWriter().println("2\n");
					break;
				case 2:
					Debug.log("Already current with 2\n");
					break;
				default:
					Debug.log("Unecognized upgrade version, only 0, 1 and 2 supported\n");
					break;
				}
			}
		}
	}

	String formatArray(String [] aVids) {
		String vids = "";
		for (String vid:aVids) {
			vids += vid + "\n";
		}
		return vids;
	}

	public String [] Titles () {
		return showTitles;
	}

	public String [] show1 () {
		return actionsShow1;
	}

	public String [] show2 () {
		return actionsShow2;
	}

	public String [] show3 () {
		return actionsShow3;
	}

	public String [] show4 () {
		return actionsShow4;
	}

	public String [] show5 () {
		return actionsShow5;
	}
}
