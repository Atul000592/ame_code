package nic.ame.master.util;

import javax.mail.MessagingException;

public class TestMailRun {
	
	
	public static void main(String[] args) {
		
		try {
			EmailSendUtil.sendmail("Bhai mail ja rahi hai ", "Specifically, the \"mAh\" stands for milliampere-hour, which is the measurement unit for the energy capacity of batteries. A battery with a capacity of 5000mAh is capable of delivering a continuous current of 1A for 5 hours, or 0.5A for 10 hours, or 5A for 1 hour", "dawdawdw");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
