package com.virtualsoundnw.videoserver;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
//import com.googlecode.objectify.Objectify;
//import com.googlecode.objectify.ObjectifyService;

public class Name {
		@Id String id; //Name is the key/ID
		String EMail;

		public Name()
		{
			id = null;
			EMail = null;
		}

		public Name(String oldName) throws Exception
		{
			Name check = Name.load(oldName);
			
			if (check == null) {
				throw new Exception("This constructor only works for existing names."); 
			} else {
				id = check.id;
				EMail = check.EMail;
			}
		}

		public Name(String newName, String newEmail) throws Exception
		{
			Name check = Name.load(newName);
			
			if (check == null) {
				id = newName;
				EMail = newEmail;
				commit();
			} else {
				throw new Exception("This constructor only works for new email addreses."); 
			}
		}

		void commit() {
			OState.objectify.put(this);
		}

		public static Name load(String cId) {
			try {
				Name check = OState.objectify.get(new Key<Name>(Name.class, cId));
				return check;
			} catch (Exception e) {
				// if it didn't exist we'll get an exception (?)
				return null;
			}
		}
		
		public static boolean exists(String checkName) {
			try {
				Name check = OState.objectify.get(new Key<Name>(Name.class, checkName));	
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
			return "Name =" + id + ", EMail =" + EMail + "\n";
		}
		
}
