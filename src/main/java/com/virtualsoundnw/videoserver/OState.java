package com.virtualsoundnw.videoserver;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;

public class OState {

	static public Objectify objectify = null;
	
	public enum state { uninit, init };
	
	@Id String id; //Cheap trick: key is always Vera Video
	state State = state.uninit;

	String [] Titles = new String[5];
	String [] Show1 = null;
	String [] Show2 = null;
	String [] Show3 = null;
	String [] Show4 = null;
	String [] Show5 = null;
	
	OState() {
		State = state.uninit;
	}

	OState(String myID) throws Exception {
		if (OState.exists(myID)) {
			throw new Exception("Can not create over an existing state, please use OState.load(OID);");
		} else {
			id = myID;
			State = state.uninit;
			commit();
		}
	}

	OState(String myID, state myState) throws Exception {
		if (OState.exists(myID)) {
			throw new Exception("Can not create over an existing state, please use OState.load(OID);");
		}
		id = myID;
		State = myState;
		commit();
	}
	
	void commit() {
		objectify.put(this);
	}

	public String [] getShow(int sNum) {
		
		String [] result = null;
		
		switch (sNum) {
			case 1:
				result = Show1;
				break;
			case 2:
				result = Show2;
				break;
			case 3:
				result = Show3;
				break;
			case 4:
				result = Show4;
				break;
			case 5:
				result = Show5;
				break;
			default:
				result = Show1;
				break;
		}
		return result;
	}
	
	public void setShow(int sNum, String [] sShows) {

		// they come out backwards, so this method reverses the order
		switch (sNum) {
			case 1:
				Show5 = sShows;
				break;
			case 2:
				Show4 = sShows;
				break;
			case 3:
				Show3 = sShows;
				break;
			case 4:
				Show2 = sShows;
				break;
			case 5:
				Show1 = sShows;
				break;
			default:
				break;
		}
	}
	
	public static OState load(String cId) {
		try {
			OState check = objectify.get(new Key<OState>(OState.class, cId));
			return check;
		} catch (Exception e) {
			// if it didn't exist we'll get an exception (?)
			return null;
		}
	}
	
	public static boolean exists(String checkOState) {
		try {
			OState check = objectify.get(new Key<OState>(OState.class, checkOState));	
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
		String result;
		
		if (State == state.init) {
			result = "id =\"Vera Video\",  state is init.\n";
		} else {
			result = "id =\"Vera Video\",  state is uninit.\n";
		}
		for (int i=0; i<5; i++)
		{
			result += "Show " + (i+1) + ", " + Titles[i] +
					", selections are " + getShow(i) + "\n";
		}
		
		if (objectify == null) {
			return result + "objectify is null.\n";
		} else {
			return result + "objectify is set.\n";
		}
	}

}
