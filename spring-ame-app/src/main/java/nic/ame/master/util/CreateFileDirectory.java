package nic.ame.master.util;

import java.io.File;


public class CreateFileDirectory {

	
	
	
	
	public static String createNewFolderOrDir(String directoryName,String forceName,String year) {
		
		
		String childDirectory = directoryName + File.separator + forceName;
		
		File file = new File(directoryName);
		
		if (!file.exists()) {
			boolean res = file.mkdir();
			if (res) {
         		File file2 = new File(childDirectory);
				if (!file2.exists()) {
        			file2.mkdir();
					return childDirectory;
				}
			}
		} else {
			
			File file2 = new File(childDirectory);
			if (!file2.exists()) {

				file2.mkdir();
				return childDirectory;
			}
              
		}
		return childDirectory;
	}
	
	
	
public static String createNewFolderOrDirUploadUserRoleOrder(String directoryName,String year) {
		
		
		String childDirectory = directoryName;
		
		File file = new File(directoryName);
		
		if (!file.exists()) {
			boolean res = file.mkdir();
			if (res) {
         		File file2 = new File(childDirectory);
				if (!file2.exists()) {
        			file2.mkdir();
					return childDirectory;
				}
			}
		} else {
			
			File file2 = new File(childDirectory);
			if (!file2.exists()) {

				file2.mkdir();
				return childDirectory;
			}
              
		}
		return childDirectory;
	}
	
}
