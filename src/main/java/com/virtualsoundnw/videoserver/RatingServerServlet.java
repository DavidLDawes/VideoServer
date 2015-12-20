package com.virtualsoundnw.videoserver;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

import com.virtualsoundnw.videoserver.Clip;
//import com.virtualsoundnw.videoserver.User;

public class RatingServerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		resp.setContentType("text/plain");
		String Q = req.getQueryString();
		if (Q == null)
		{
			Debug.log("Rating servlet got no query\n");
			resp.getWriter().println("ERROR! Badly formed request, no query found, unable to process. Sorry!\nExpected ?User=abDocc937q2,Clip=3u47agK9Vjg,rating=4\n");
		} else {
			if (Q.startsWith("User="))
			{
				Q = Q.substring(5);
				String args[] = Q.split(",");
				if (args.length == 3)
				{
					String clnEmail = Behavior.cleanEmail(args[0]);
					if (clnEmail == null) {
						Debug.log("ERROR! Rating servlet cleanEmail got null\n");
						resp.getWriter().println("ERROR! Unable to create this record, cleaned email address=\""+Behavior.cleanEmail(args[0])+"\" may not be long enough. Sorry!\n");
					} else {
						if (Behavior.exists(clnEmail)) {
							if (args[1].startsWith("Clip="))
							{	
								String sClip = args[1].substring(5);
								char cRating = 255;
								
								if (args[2].startsWith("Rating="))
								{
									cRating = args[2].charAt(7);
									if (cRating < '0' || cRating > '5') 
									{
										Debug.log("ERROR rating is not in the range 0-5.\n");
										resp.getWriter().println("ERROR rating out of range\n");
									} else {
										
										if (Clip.exists(sClip)) {
											Clip rateClip = Clip.load(sClip);
											Debug.log("DEBUG Rating clip " + sClip );
											switch (cRating)
											{
											case '0':
												rateClip.RateClip(clnEmail,(byte) 0);
												resp.getWriter().println("email address=\""+clnEmail+"\" rated clip\""+sClip+" 0, must have disliked it.\n");
												break;
											case '1':
												rateClip.RateClip(clnEmail,(byte) 1);
												resp.getWriter().println("email address=\""+clnEmail+"\" rated clip\""+sClip+" 1, didn't like it much.\n");												
												break;
											case '2':
												rateClip.RateClip(clnEmail,(byte) 2);
												resp.getWriter().println("email address=\""+clnEmail+"\" rated clip\""+sClip+" 2, below average.\n");
												break;
											case '3':
												rateClip.RateClip(clnEmail,(byte) 3);
												resp.getWriter().println("email address=\""+clnEmail+"\" rated clip\""+sClip+" 3, average.\n");
												break;
											case '4':
												rateClip.RateClip(clnEmail,(byte) 4);
												resp.getWriter().println("email address=\""+clnEmail+"\" rated clip\""+sClip+" 4, must have liked it.\n");
												break;
											case '5':
												rateClip.RateClip(clnEmail,(byte) 5);
												resp.getWriter().println("email address=\""+clnEmail+"\" rated clip\""+sClip+" 5, loved it.\n");
												break;
											}

										}
									}
								}
							}
							
							
						} else {
							Behavior myUsr = new Behavior();
							myUsr.id = clnEmail;
							if (args[1].startsWith("name="))
							{
								String clnName = Behavior.cleanName( args[1].substring(5));
								Debug.log("INFO Got clean email \"" + clnEmail +"\"and clean name \"" + clnName + "\"prompts\n");
								if (clnName == null) 
								{
									Debug.log("ERROR! Unable to create User servlet already exists\n");
									resp.getWriter().println("ERROR! Unable to create this record, cleaned name=\""+Behavior.cleanName( args[1].substring(5))+"\" may not be long enough. Sorry!\n");
								} else {
									myUsr.UserName = clnName;
									// need to check for name reuse
									myUsr.commit();
									Debug.log("INFO 200 OK. Email=\""+clnEmail+"\", Name=\""+clnName+"\"\n");
									resp.getWriter().println("200 OK. Email=\""+clnEmail+"\", Name=\""+clnName+"\"\n");
								}
							} else {
								Debug.log("ERROR! Expected name=\n");
								resp.getWriter().println("ERROR! Improper formatting, expected name=. Sorry!\n");
							}
						}
					}
				} else {
					if (args.length == 1)
					{
						Behavior myUsr = Behavior.load(args[0]);
						if (myUsr == null) 
						{
							Debug.log("ERROR! EMail not found in single arg ==  lookup version. Fail!\n");
							resp.getWriter().println("Email not found. Sorry!\n");
						} else {
							Debug.log("INFO User found. Email=\""+myUsr.id+"\", Name=\""+myUsr.UserName+"\"\n");
							resp.getWriter().println("User found. Email=\""+myUsr.id+"\", Name=\""+myUsr.UserName+"\"\n");
						}
					} else {
						Debug.log("WARN! Unrecognized query (the tail bit of the html after the ?)\n");
						resp.getWriter().println("Unrecognized query\n");
					}
				}
			} else {
				if (Q.startsWith("list"))
				{
					Query<Clip> q = OState.objectify.query(Clip.class);
					for (Clip allClips: q) {
						resp.getWriter().println(allClips.toString());
					
					}
				}
			}
		}
	}

}
