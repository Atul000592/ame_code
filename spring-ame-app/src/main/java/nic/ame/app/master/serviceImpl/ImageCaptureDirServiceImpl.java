package nic.ame.app.master.serviceImpl;

import java.io.File;

import org.springframework.stereotype.Service;

import nic.ame.app.master.service.ImageCaptureDirService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CreateFileDirectory;

@Service
public class ImageCaptureDirServiceImpl implements ImageCaptureDirService{

	@Override
	public String DirectoryCreated(String forceName,String Year) {
		
		String directoryName = CommonConstant.File_PATH_IMAGE_CAPTURE+File.separator+Year;
		return CreateFileDirectory.createNewFolderOrDir(directoryName,forceName,Year); 
	}

}
