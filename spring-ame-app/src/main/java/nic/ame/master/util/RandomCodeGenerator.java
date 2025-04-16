package nic.ame.master.util;


import java.util.Date;

public class RandomCodeGenerator {

	
	private static final int LENGTH_ROLE_GENERATOR=9;
	private static final int LENGTH_FORCEPERSONAL_RANDOM_CODE=3;
	
	public static String getTransactionalIdUserRole() {
		StringBuffer buffer=new StringBuffer();
		
		String  randomCode=RandomGenerator.getRandomOneTimePassword(LENGTH_ROLE_GENERATOR);
		buffer.append(randomCode);
		int day=UserDateUtil.getDay(new Date());
		int month=UserDateUtil.getMonth(new Date());
		int year=UserDateUtil.getYear(new Date());
		System.out.println("year>>>>>>>>>>>>>>>"+year+"month>>>>>>>>>>>>>>>"+month);
		buffer.append(month).append(day);
		return buffer.toString().trim();
	}
	
	
	
	public static String generateForcePersonalId(String IrlaNo,String forceNo) {
		
		if(IrlaNo!=null && forceNo!=null) {
			StringBuffer buffer=new StringBuffer();
			String  randomCode=RandomGenerator.getRandomOneTimePassword(LENGTH_FORCEPERSONAL_RANDOM_CODE);
			buffer.append(forceNo.trim()).append(IrlaNo.trim());
			buffer.append(randomCode);
			return buffer.toString().trim();
		}
		else {
			
			return null;
		}
		
		
		

		
	}
}
