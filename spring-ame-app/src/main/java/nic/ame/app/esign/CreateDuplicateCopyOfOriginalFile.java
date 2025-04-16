package nic.ame.app.esign;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;

@Service
public class CreateDuplicateCopyOfOriginalFile {

	
	 public  File copyFileToTempFolder(String originalFilePath) throws IOException {
	        // Get the original file
	        File originalFile = new File(originalFilePath);

	        // Validate the file exists
	        if (!originalFile.exists()) {
	            throw new IOException("Original file does not exist: " + originalFilePath);
	        }

	        // Get the parent directory of the original file
	        File parentDir = originalFile.getParentFile();

	        // Create a temporary folder in the same directory as the original file
	        File tempFolder = new File(parentDir, "temp");
	        if (!tempFolder.exists()) {
	            if (!tempFolder.mkdir()) {
	                throw new IOException("Failed to create temp folder: " + tempFolder.getAbsolutePath());
	            }
	        }

	        // Path for the file inside the temp folder
	        File tempFile = new File(tempFolder, originalFile.getName());

	        // Copy the original file into the temp folder
	        Files.copy(originalFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	        // Return the temporary file
	        return tempFile;
	    }
	 
	 public  boolean fileExists(String filePath) {
	        File file = new File(filePath);
	        return file.exists();
	    }
}
