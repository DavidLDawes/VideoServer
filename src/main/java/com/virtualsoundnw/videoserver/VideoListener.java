package com.virtualsoundnw.videoserver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

//import com.virtualsoundnw.videoserver.Clip;
import com.virtualsoundnw.videoserver.Behavior;
import com.virtualsoundnw.videoserver.OState;
public class VideoListener implements ServletContextListener {

	private static final String app = "Vera Video";

	public static final String [] updateValues0 = {
		"Smoosh,Gold,Not Mine,wQcYU_VRjB8",
		"Nurses, Mile After Mile,Not Mine,zJErDQjhwf8",
		"Common Market,,,bow8Qfs9RUg",
		"Smoosh,Rad,Not Mine,gQcMea5g7B0"
	};
	
	public static final String [] updateValues1 = {
		"Smoosh,Rad,Not Mine,gQcMea5g7B0"
	};

	
	public static final String [] updateBlog0 = {
		"October Veracity,http://virtualsoundnw.blogspot.com/2012/10/october-veracity.html",
		"Calvin Johnson,http://virtualsoundnw.blogspot.com/2012/08/calvin-johnson-tape-release-show-at.html"
	};

	public static final String [] updateBlog1 = {
		"October Veracity,http://virtualsoundnw.blogspot.com/2012/10/october-veracity.html",
	};
	
	
	public static OState thisState;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		boolean settled = false;
		
		if (OState.objectify == null) {
			OState.objectify = ObjectifyService.begin();
		}

		ObjectifyService.factory().register(Behavior.class);
		ObjectifyService.factory().register(Name.class);
		//ObjectifyService.factory().register(Song.class);
		//ObjectifyService.factory().register(Clip.class);
		ObjectifyService.factory().register(OState.class);
		

		OState myState = OState.load(app);
		if (myState != null) {
			// DB has been set up at least once
			switch (myState.State)
			{
			case init:
				// We're good. Get the apps
				VideoServerServlet.showTitles = myState.Titles;
				VideoServerServlet.actionsShow1 = myState.getShow(1);
				VideoServerServlet.actionsShow2 = myState.getShow(2);
				VideoServerServlet.actionsShow3 = myState.getShow(3);
				VideoServerServlet.actionsShow4 = myState.getShow(4);
				VideoServerServlet.actionsShow5 = myState.getShow(5);
				settled = true;
				break;

			case uninit:
				// DB values NOT present yet
				myState.State = OState.state.init;
				break;
			
			default:
				// Huh?
				myState.State = OState.state.init;
				break;
			}
		} else {
			// DB has NOT been set up yet
			try {
				myState = new OState(app, OState.state.uninit);
				myState.Titles[4] = "Jeffrey Lewis";
				myState.Titles[3] = "Set It Off";
				myState.Titles[2] = "L. Stirling";
				myState.Titles[1] = "CA Honeydrops";
				myState.Titles[0] = "Delicate Steve";
				myState.setShow(1, new String [] {
						"Purchase tickets to Jeffery Lewis and the Junkyard with Kimya Dawson,http://www.etix.com/ticket/online/performanceSearch.jsp?performance_id=1656968",
						"Web site,http://www.thejeffreylewissite.com/",
						"Jeffrey Lewis,,,jfsj9-URaoA",		
						"Jeffrey Lewis Vera show blog,http://virtualsoundnw.blogspot.com/2012/01/peter-stampfel-jeffrey-lewis-and-dust.html" }
				);
				myState.setShow(2, new String [] {
						"Purchase tickets to Set It Off Sparks the Rescue and Handguns and Afterwards and Four Minute Mile,http://www.etix.com/ticket/online/performanceSearch.jsp?performance_id=1655405",
						"Facebook,https://www.facebook.com/setitoffband",
						"YouTube Channel,http://www.youtube.com/user/setitoffband",
						"Set It Off,I Promise (acoustic),Not Mine,tcPtI-Z8ROw" }
				);
				myState.setShow(3, new String [] {
						"Purchase tickets to Lindsey Stirling and Guests,http://www.etix.com/ticket/online/performanceSearch.jsp?performance_id=1652597",
						"Web site,http://lindseystirlingviolin.com/",
						"YouTube channel,http://www.youtube.com/user/lindseystomp",
						"Lindsey Stirling,Assasin's Creed III,Not Mine,MOg8Cz9yfWg" }
				);
				myState.setShow(4, new String [] {
						"Purchase tickets to California Honeydrops plus Guests,http://www.etix.com/ticket/online/performanceSearch.jsp?performance_id=1664268",
						"Web site,http://www.cahoneydrops.com/",
						"My Space,http://us.myspace.com/thecaliforniahoneydrops",
						"Facebook,https://www.facebook.com/cahoneydrops",
						"California Honeydrops,When It Ws Wrong,Not Mine,gC-7hS51snU"}
				);
				myState.setShow(5, new String [] {
						"Purchase tickets to Dekicate Steve Dana Buoy (of Akron/Family),http://www.etix.com/ticket/online/performanceSearch.jsp?performance_id=1664265",
						"Facebook,https://www.facebook.com/DelicateSteve",
						"Positive Force on iTunes,http://www.smarturl.it/PositiveForce",
						"Web site,http://www.delicatesteve.com",
						"Delicate Steve,The Ballad of Speck and Pebble,Not Mine,t8duejMb9oM",
						"Delicate Steve,Wally Wilder,Not Mine,MJ7chrLEZm4",
						"Delicate Steve,Buttefly,Not Mine,zRVpW-z3g2g"}
						);

				myState.State = OState.state.init;
				myState.commit();
			} catch (Exception e) {
				myState.State = OState.state.uninit;
				// need error handling
			}
		}
		if (myState != null) {
			if (!settled) {
				VideoServerServlet.showTitles = myState.Titles;
				VideoServerServlet.actionsShow1 = myState.getShow(1);
				VideoServerServlet.actionsShow2 = myState.getShow(2);
				VideoServerServlet.actionsShow3 = myState.getShow(3);
				VideoServerServlet.actionsShow4 = myState.getShow(4);
				VideoServerServlet.actionsShow5 = myState.getShow(5);
				settled = true;
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
