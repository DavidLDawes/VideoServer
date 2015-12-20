package com.virtualsoundnw.videoserver;

import javax.persistence.Id;
import com.googlecode.objectify.Key;

public class Song {
	@Id String id; //YouTube song string is the key/ID
	String Band;
	String SongName;
	String Theme;
	String Genre;


	public Song()
	{
		id = null;
		Band = null;
		SongName = null;
		Theme = null;
		Genre = null;
	}

	public Song(String youTubeId) throws Exception
	{
		Song check = Song.load(youTubeId);
		
		if (check == null) {
			throw new Exception("This constructor only works for existing songs."); 
		} else {
			id = check.id;
			Band = check.Band;
			SongName = check.SongName;
			Theme = check.Theme;
			Genre = check.Genre;
		}
	}

	public Song(String youTubeId, String sName, String sBand, String sTheme, String sGenre) throws Exception
	{
		id = youTubeId;
		Band = sBand;
		SongName = sName;
		Theme = sTheme;
		Genre = sGenre;
		commit();
	}

	
	public static boolean exists(String checkSong) {
		try {
			Song check = OState.objectify.get(new Key<Song>(Song.class, checkSong));	
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

	public static Song load(String sId) {
		try {
			Song check = OState.objectify.get(new Key<Song>(Song.class, sId));
			return check;
		} catch (Exception e) {
			// if it didn't exist we'll get an exception (?)
			return null;
		}
	}
	
	void commit() {
		OState.objectify.put(this);
	}

	public String toString() {
		String sSong = "YoutTube ID = '" + id + "'";
		if (Genre.length()>0) {
			if (Theme.length()>0) {
				if (SongName.length()>0) {
					if (Band.length()>0) {
						sSong = sSong + ", Band=" + Band + ", Song=" + SongName + ", theme =" + Theme + ", Genre =" + Genre + "\n";
					} else { 
						sSong = sSong + ", Song=" + SongName + ", theme =" + Theme + ", Genre =" + Genre + "\n";;
					}
					
				} else {
					if (Band.length()>0) {
						sSong = sSong + ", Band="+Band + ", theme =" + Theme + ", Genre =" + Genre + "\n";;
					} else {
						sSong = sSong + ", theme =" + Theme + ", Genre =" + Genre + "\n";;
					}
					
				}
				
			} else {
				if (SongName.length()>0) {
					if (Band.length()>0) {
						sSong = sSong + ", Band='"+Band+", Song=" + SongName + ", Genre =" + Genre + "\n";;
					} else { 
						sSong = sSong + ", Song=" + SongName + ", Genre =" + Genre + "\n";
					}
					
				} else {
					if (Band.length()>0) {
						sSong = sSong + ", Band='"+Band+", Genre =" + Genre + "\n";
					} else {
						sSong = sSong + ", Genre =" + Genre + "\n";;
					}
				}
			}
			
		} else {
			if (Theme.length()>0) {
				if (SongName.length()>0) {
					if (Band.length()>0) {
						sSong = sSong + ", Band="+Band+", Song=" + SongName + ", theme =" + Theme + "\n";
					} else { 
						sSong = sSong + ", Song=" + SongName + ", theme =" + Theme + "\n";
					}
					
				} else {
					if (Band.length()>0) {
						sSong = sSong + ", Band="+Band + ", theme =" + Theme + "\n";
					} else {
						sSong = sSong + ", theme =" + Theme + "\n";
					}
				}
			} else {
				if (SongName.length()>0) {
					if (Band.length()>0) {
						sSong = sSong + ", Band='"+Band+", Song=" + SongName + "\n";
					} else { 
						sSong = sSong + ", Song=" + SongName + "\n";
					}
					
				} else {
					if (Band.length()>0) {
						sSong = sSong + ", Band='"+Band+"\n";
					} else {
						sSong = sSong + ", no details\n";
					}
					
				}
				
			}
		}
		return sSong; 
	}
	
}
