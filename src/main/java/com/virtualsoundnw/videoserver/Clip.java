package com.virtualsoundnw.videoserver;

import javax.persistence.Id;
import com.virtualsoundnw.videoserver.Song;

import com.googlecode.objectify.Key;
//import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;

public class Clip {
	@Id String id; //YouTube Video ID is the key/ID
	
	String Date;
	private String UserRatedMe0;
	private String UserRatedMe1;
	private String UserRatedMe2;
	private String UserRatedMe3;
	private String UserRatedMe4;
	private String UserRatedMe5;
	
	int totalRated;
	
	int rated0;
	int rated1;
	int rated2;
	int rated3;
	int rated4;
	int rated5;
	
	public final float noAverage = -1;
	
	float AverageRating;

	public Clip()
	{
		rated0 = 0;
		rated1 = 0;
		rated2 = 0;
		rated3 = 0;
		rated4 = 0;
		rated5 = 0;
		totalRated = 0;
		AverageRating = noAverage;
		UserRatedMe0 = "";
		UserRatedMe1 = "";
		UserRatedMe2 = "";
		UserRatedMe3 = "";
		UserRatedMe4 = "";
		UserRatedMe5 = "";
	}

	public Clip(String YouTubeID) throws Exception
	{
		Clip check = Clip.load(YouTubeID);
		if (check == null) {
			throw new Exception("This constructor only works for existing clips."); 
		} else {
			Date = check.Date;
			
			UserRatedMe0 = check.UserRatedMe0;
			UserRatedMe1 = check.UserRatedMe1;
			UserRatedMe2 = check.UserRatedMe2;
			UserRatedMe3 = check.UserRatedMe3;
			UserRatedMe4 = check.UserRatedMe4;
			UserRatedMe5 = check.UserRatedMe5;

			totalRated = check.totalRated;
			
			rated0 = check.rated0;
			rated1 = check.rated1;
			rated2 = check.rated2;
			rated3 = check.rated3;
			rated4 = check.rated4;
			rated5 = check.rated5;
			
			AverageRating = check.AverageRating;
		}

	}
	
	public Clip(String YouTubeID, String name, String band, String theme, String date) throws Exception
	{

		String proposed = YouTubeID;
		if (proposed.equalsIgnoreCase("more guitar")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("sound check")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("spoken word")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("bjorke york")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("kick it off")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("packed bagz")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("stormy days")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("more guitar")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("reflections")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("saturdayzed")) {
			proposed=name;
			name = YouTubeID;
		} else if (proposed.equalsIgnoreCase("he's a slut")) {
			proposed=name;
			name = YouTubeID;
		}
		
		id = proposed;
		
		if (Clip.exists(id)) {
			Clip loadMe = Clip.load(id);
			if (loadMe == null) {
				throw new Exception("Unable to load existing record");
			} else {
				Date = loadMe.Date;
				UserRatedMe0 = loadMe.UserRatedMe0;
				UserRatedMe1 = loadMe.UserRatedMe1;
				UserRatedMe2 = loadMe.UserRatedMe2;
				UserRatedMe3 = loadMe.UserRatedMe3;
				UserRatedMe4 = loadMe.UserRatedMe4;
				UserRatedMe5 = loadMe.UserRatedMe5;
				CalcRatings();
			}
		} else {
			if (name.equalsIgnoreCase("veracity")) {
				name = theme;
				theme = "Veracity";
			} else if (name.equalsIgnoreCase("last show")) {
				name = theme;
				theme = "Last Show";
			} else if (name.equalsIgnoreCase("potter's birthday")) {
				name = theme;
				theme = "Potter's Birthday";
			} else if (name.equalsIgnoreCase("josh's birthday")) {
				name = theme;
				theme = "Josh's Birthday";
			} else if (name.equalsIgnoreCase("calvin johnson tape fest")) {
				name = theme;
				theme = "Calvin Johnson Tape Fest";
			} else if (band.equalsIgnoreCase("last show")) {
				band = theme;
				theme = "Last Show";
			} else if (band.equalsIgnoreCase("potter's birthday")) {
				band = theme;
				theme = "Potter's Birthday";
			} else if (band.equalsIgnoreCase("josh's birthday")) {
				band = theme;
				theme = "Josh's Birthday";
			} else if (band.equalsIgnoreCase("calvin johnson tape fest")) {
				band = theme;
				theme = "Calvin Johnson Tape Fest";
			}
			if (band.length() < 1) {
				band = name;
				name = "";
			}
			
			Song checkSong = Song.load(id);
			if (checkSong == null) {
				checkSong = new Song(id, name, band, theme, date);
				checkSong.commit();
			}
			Date = date;

			UserRatedMe0 = "";
			UserRatedMe1 = "";
			UserRatedMe2 = "";
			UserRatedMe3 = "";
			UserRatedMe4 = "";
			UserRatedMe5 = "";
			CalcRatings();
			commit();
		}
	}

	
	void RateClip(String user, byte rating) {

		RemoveRating(user);
		
		Behavior RateMe = Behavior.load(user);
		
		if (RateMe == null) {
			
		} else {
					
			switch (rating) {
			case 0:
				UserRatedMe0 = UserRatedMe0 + " " + user;
				CalcRatings();
				commit();
				break;
			case 1:
				UserRatedMe1 = UserRatedMe1 + " " + user;
				CalcRatings();
				commit();
				break;
			case 2:
				UserRatedMe2 = UserRatedMe2 + " " + user;
				CalcRatings();
				commit();
				break;
			case 3:
				UserRatedMe3 = UserRatedMe3 + " " + user;
				CalcRatings();
				commit();
				break;
			case 4:
				UserRatedMe4 = UserRatedMe4 + " " + user;
				CalcRatings();
				commit();
				break;
			case 5:
				UserRatedMe5 = UserRatedMe5 + " " + user;
				CalcRatings();
				commit();
				break;
			default:
				// if they didn't choose 0-5, then >5 = 5 and <0 = 0. 
				if (rating > 5) {
					UserRatedMe5 = UserRatedMe5 + " " + user;
					CalcRatings();
					commit();
					break;
				} else {
					UserRatedMe0 = UserRatedMe0 + " " + user;
					CalcRatings();
					commit();
					break;
				}
			}
		}
	}

	boolean RemoveRating(String user)
	{
		UserRatedMe0 = Remove1Rating(UserRatedMe0, user);
		UserRatedMe1 = Remove1Rating(UserRatedMe1, user);
		UserRatedMe2 = Remove1Rating(UserRatedMe2, user);
		UserRatedMe3 = Remove1Rating(UserRatedMe3, user);
		UserRatedMe4 = Remove1Rating(UserRatedMe4, user);
		UserRatedMe5 = Remove1Rating(UserRatedMe5, user);
		CalcRatings();
		return false;
		
	}

	String Remove1Rating(String UR, String user)
	{
		UR.replace("[ ]+"+user+"[ ]+", " ");
		UR.replace(user+"[ ]+", " ");
		UR.replace("[ ]+"+user, " ");
		UR.replace("  ", " ");
		UR.trim();
		return UR;
	}
	
	
	void CalcRatings()
	{

		rated0 = 0;
		rated1 = 0;
		rated2 = 0;
		rated3 = 0;
		rated4 = 0;
		rated5 = 0;
		String[] rNext = UserRatedMe0.split("[ ]+");
		rated0 = rNext.length-1; 
		rNext = UserRatedMe1.split("[ ]+");
		rated1 = rNext.length-1; 
		rNext = UserRatedMe2.split("[ ]+");
		rated2 = rNext.length-1; 
		rNext = UserRatedMe3.split("[ ]+");
		rated3 = rNext.length-1; 
		rNext = UserRatedMe4.split("[ ]+");
		rated4 = rNext.length-1; 
		rNext = UserRatedMe5.split("[ ]+");
		rated5 = rNext.length-1;
		
		totalRated = rated0 + rated1 + rated2 + rated3 + rated4 + rated5;
		if (totalRated > 0)

		{
			AverageRating = ((float) rated1 + 2.0f * (float) rated2 + 
				  3.0f * (float) rated3 + 4.0f * (float) rated4 +
				  5.0f * (float) rated5 ) / totalRated;
		} else {
				totalRated = 0;
				AverageRating = noAverage;
		}
	}
	
	void commit() {
		CalcRatings();
		OState.objectify.put(this);
	}

	public static Clip load(String cId) {
		try {
			Clip check = OState.objectify.get(new Key<Clip>(Clip.class, cId));
			return check;
		} catch (Exception e) {
			// if it didn't exist we'll get an exception (?)
			return null;
		}
	}
	
	public static boolean exists(String checkClip) {
		try {
			Clip check = OState.objectify.get(new Key<Clip>(Clip.class, checkClip));	
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

	public String toString() {
		if (AverageRating < 0.0f || AverageRating > 5.0f) {
			return "YouTube ID ='" + id +"', No Rating, Date =" + Date + "\n"; 
		} else {
			String ClipRating = "Users rated me 0, count =" + rated0 + ", Users ='" + UserRatedMe0 + "'\n";
			ClipRating = ClipRating + "Users rated me 1, count =" + rated1 + ", Users ='" + UserRatedMe1 + "'\n ";
			ClipRating = ClipRating + "Users rated me 2, count =" + rated2 + ", Users ='" + UserRatedMe2 + "'\n ";
			ClipRating = ClipRating + "Users rated me 3, count =" + rated3 + ", Users ='" + UserRatedMe3 + "'\n";
			ClipRating = ClipRating + "Users rated me 4, count =" + rated4 + ", Users ='" + UserRatedMe4 + "'\n";
			ClipRating = ClipRating + "Users rated me 5, count =" + rated5 + ", Users ='" + UserRatedMe5 + "'\n";
			return "YouTube ID ='" + id +"', Rating =" + AverageRating + ", Date =" + Date + "\n" + ClipRating; 
		}
	}
}
