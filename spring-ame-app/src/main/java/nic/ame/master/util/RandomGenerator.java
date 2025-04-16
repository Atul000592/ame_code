package nic.ame.master.util;

import java.util.Random;

import nic.ame.constant.CommonConstant;

public class RandomGenerator {

	public static String getRandomOneTimePassword() {

		int length = CommonConstant.OTP_LENGTH;
		char[] choices = "123456789".toCharArray();

		StringBuilder randomString = new StringBuilder(length);

		Random random = new Random();

		for (int i = 0; i<length; ++i) {

			randomString.append(choices[random.nextInt(choices.length)]);
		}
		return randomString.toString();
	}
	
	
	
	// randomCodeGenerator for variable length
	public static String getRandomOneTimePassword(int length) {

		
		char[] choices = "123456789".toCharArray();

		StringBuilder randomString = new StringBuilder(length);

		Random random = new Random();

		for (int i = 0; i<length; ++i) {

			randomString.append(choices[random.nextInt(choices.length)]);
		}
		return randomString.toString();
	}
	
	
	
	
}
