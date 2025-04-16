package nic.ame.app.master.service;


public interface DecryptAESServiceForPasswordEncryption {
	
	
	public String getDecryptAESPassword(String password,String ivHex,String keyHex);
 
}
