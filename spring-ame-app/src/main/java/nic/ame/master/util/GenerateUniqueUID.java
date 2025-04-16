package nic.ame.master.util;

import java.util.UUID;

public final class GenerateUniqueUID {

	
	public static String GenerateUUIDWithTimeStamp() {
		
		 UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
	}
	
	
}
