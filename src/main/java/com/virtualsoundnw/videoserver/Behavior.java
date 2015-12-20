package com.virtualsoundnw.videoserver;

import javax.persistence.Id;

import com.virtualsoundnw.videoserver.Clip;
import com.virtualsoundnw.videoserver.Name;

import com.googlecode.objectify.Key;
//import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;

public class Behavior {
	@Id String id; //SMTP e-mail address is the key/ID
	String UserName;

	final static int maxID = 255;
	final static int minID = 8;
	final static int maxUName = 24;
	final static int minUName = 3;
	
	String ClipsRated5;
	String ClipsRated4;
	String ClipsRated3;
	String ClipsRated2;
	String ClipsRated1;
	String ClipsRated0;

	public Behavior() {
		// default constructor, nothing passed in
		// if you create it this way it is not bound to the data store
		// you'll have to do a .commit() to get it written in that case
		id = null;
		UserName = "";
		ClipsRated0 = "";
		ClipsRated1 = "";
		ClipsRated2 = "";
		ClipsRated3 = "";
		ClipsRated4 = "";
		ClipsRated5 = "";
	}

	public Behavior(String eMail, String name) throws Exception
	{
		String clnEMail = cleanup(eMail, maxID);
		if (clnEMail.length() < minID) {
			id = null;
			throw new Exception("Cleaned up EMail address =\""+clnEMail+"\" is too short.");			
		}

		String clnName = cleanup(name, maxUName);
		if (clnName.length() < minUName) {
			id = null;
			throw new Exception("Cleaned up Name =\""+clnName+"\" is too short.");			
		}

		Behavior myU = Behavior.load(clnEMail);
		if (myU == null) 
		{
			try {
				Name myN = new Name(clnName, clnEMail);
				id = clnEMail;
				UserName = clnName;
				ClipsRated0 = "";
				ClipsRated1 = "";
				ClipsRated2 = "";
				ClipsRated3 = "";
				ClipsRated4 = "";
				ClipsRated5 = "";
				commit();
			} catch (Exception e) {
				id = null;
				throw new Exception("Constructor failed."); 
			}
		} else {
			if ( clnName.contentEquals(myU.UserName) )
			{
				ClipsRated0 = myU.ClipsRated0;
				ClipsRated1 = myU.ClipsRated1;
				ClipsRated2 = myU.ClipsRated2;
				ClipsRated3 = myU.ClipsRated3;
				ClipsRated4 = myU.ClipsRated4;
				ClipsRated5 = myU.ClipsRated5;
			} else {
				id = null;
				throw new Exception("Constructor failed, unable to add Name."); 
			}
		}
	}


	Behavior(String eMail)
	{
		String clnEMail = cleanup(eMail, maxID);
		if (clnEMail.length() < minID) {
			id = null;
		} else {
			Behavior myU = Behavior.load(clnEMail);
			if (myU == null)
			{
				id = null;
				UserName = "";
			} else {
				id = myU.id;
				UserName = myU.UserName;
				ClipsRated0 = myU.ClipsRated0;
				ClipsRated1 = myU.ClipsRated1;
				ClipsRated2 = myU.ClipsRated2;
				ClipsRated3 = myU.ClipsRated3;
				ClipsRated4 = myU.ClipsRated4;
				ClipsRated5 = myU.ClipsRated5;
			}
		}
	}
	
	
	void RateClip(String clip, byte rating) {

		RemoveRating(clip);
		
		Clip RateMe = Clip.load(clip);
		
		if (RateMe == null) {
			
		} else {
					
			RateMe.RemoveRating(id);

			switch (rating) {
			case 0:
				ClipsRated0 = ClipsRated0 + " " + clip;
				RateMe.RateClip(id, rating);
				break;
			case 1:
				ClipsRated1 = ClipsRated1 + " " + clip;
				RateMe.RateClip(id, rating);
				break;
			case 2:
				ClipsRated2 = ClipsRated2 + " " + clip;
				RateMe.RateClip(id, rating);
				break;
			case 3:
				ClipsRated3 = ClipsRated3 + " " + clip;
				RateMe.RateClip(id, rating);
				break;
			case 4:
				ClipsRated4 = ClipsRated4 + " " + clip;
				RateMe.RateClip(id, rating);
				break;
			case 5:
				ClipsRated5 = ClipsRated5 + " " + clip;
				RateMe.RateClip(id, rating);
				break;
			default:
				// if they didn't choose 0-5, them >5 = 5 and <0 = 0. 
				if (rating > 5) {
					ClipsRated5 = ClipsRated5 + " " + clip;
					RateMe.RateClip(id, (byte) 5);
					break;
				} else {
					ClipsRated0 = ClipsRated0 + " " + clip;
					RateMe.RateClip(id, (byte) 0);
					break;
				}
			}
			RateMe.commit();
		}
		commit();
	}
	

	boolean RemoveRating(String clip)
	{
		ClipsRated0 = Remove1Rating(ClipsRated0, clip);
		ClipsRated1 = Remove1Rating(ClipsRated1, clip);
		ClipsRated2 = Remove1Rating(ClipsRated2, clip);
		ClipsRated3 = Remove1Rating(ClipsRated3, clip);
		ClipsRated4 = Remove1Rating(ClipsRated4, clip);
		ClipsRated5 = Remove1Rating(ClipsRated5, clip);
		
		Clip thisClip = Clip.load(clip);
		thisClip.RemoveRating(id);
		thisClip.commit();
		this.commit();
		return false;
		
	}

	String Remove1Rating(String CR, String clip)
	{
		CR.replace("[ ]+"+clip+"[ ]+", " ");
		CR.replace(clip+"[ ]+", " ");
		CR.replace("[ ]+"+clip, " ");
		CR.replace("  ", " ");
		CR.trim();
		return CR;
	}
	
	
	void commit() {
		OState.objectify.put(this);
	}

	public static Behavior load(String cId) {
		try {
			Behavior check = OState.objectify.get(new Key<Behavior>(Behavior.class, cId));
			return check;
		} catch (Exception e) {
			// if it didn't exist we'll get an exception (?)
			return null;
		}
	}
	
	public static boolean exists(String checkUser) {
		try {
			Behavior check = OState.objectify.get(new Key<Behavior>(Behavior.class, checkUser));	
			if (check == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// if it didn't exist we'll get an exception (?)
			return false;
		}
	}
	
	public static String cleanup( String unClean, int maxLength) {
		String result;
		result = unClean.replace(" ", "").replace(":", "").replace("%", "").replace("$", "").replace("~", "").replace(";", "").replace("?", "").replace("=", "").replace("{", "").replace("}", "").replace("[", "").replace("]", "");
		for (char c = 0; c<32; c++) {
			result = result.replace(String.valueOf(c), "");
		}
		if (result.length() > maxLength) 
		{
			result = result.substring(0, maxLength);
		}
		return result;
	}

	public static String cleanEmail( String unClean) {
		String clnE = cleanup(unClean, maxID);
		if (clnE.length() < minID)
		{
			return null;
		} else {
			return clnE;
		}
	}

	public static String cleanName( String unClean) {
		String clnN = cleanup(unClean, maxUName);
		if (clnN.length() < minUName)
		{
			return null;
		} else {
			return clnN;
		}
	}

	public String toString() {
		return "User =\"" + UserName + "\", EMail =\"" + id + "\"\n";
	}
	
}
