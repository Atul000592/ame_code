package nic.ame.app.master.service;

import java.util.List;

import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.master.ref.entity.CategoryShapeMaster;

public interface AmeShapeCategoryDurationService {

	
	
	public List<DropDownDto> getAmeDuration();
	public List<CategoryShapeMaster> getAmeShapeType(String shapeTypeCode);
	public List<DropDownDto> getAmeCategoryType();
	
}
