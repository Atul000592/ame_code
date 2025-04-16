package nic.ame.master.util;

import java.util.Date;
import java.util.Random;



public class UniqueIdGeneratorForReportingOfficer {

	
	public static final String REPORTING_OFFICER="RO";
	public static  String genrateReportingOfficerUniqueId(Integer forceNo,String unitId) {
		Random random = new Random();
		int randomKey=random.nextInt(9000);
	    StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder
	    .append(REPORTING_OFFICER)
	    .append(forceNo).append(unitId)
	    .append(randomKey)
	    .append(new Date().getYear());
		return stringBuilder.toString();
	}
}
