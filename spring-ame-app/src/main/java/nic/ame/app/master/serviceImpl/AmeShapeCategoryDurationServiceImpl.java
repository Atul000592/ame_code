package nic.ame.app.master.serviceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.board.member.repository.RefCategoryStatusTypeRepo;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefDurationMaster;
import nic.ame.app.master.ref.entity.CategoryShapeMaster;
import nic.ame.app.master.ref.entity.repo.RefDurationMasterRepo;
import nic.ame.app.master.ref.entity.repo.RefShapeRepo;
import nic.ame.app.master.service.AmeShapeCategoryDurationService;

@Service
public class AmeShapeCategoryDurationServiceImpl  implements AmeShapeCategoryDurationService{

	@Autowired
	private RefShapeRepo refShapeRepo;
	
	
	@Autowired
	private RefCategoryStatusTypeRepo categoryStatusTypeRepo;
	
	
	
	@Autowired
	private RefDurationMasterRepo refDurationMasterRepo;
	
	
	
	

	//===================================Duration=========================================//
	@Override
	public List<DropDownDto> getAmeDuration() {
	  List<DropDownDto> downDtos=refDurationMasterRepo.findAll()
			  .stream()
			  .map(AmeShapeCategoryDurationServiceImpl::getDurationList)
			 .collect(Collectors.toList());
	  return downDtos;
	   
	
	}

	public static DropDownDto getDurationList(RefDurationMaster refDurationMaster) {
		DropDownDto downDto=new DropDownDto();
		downDto.setcValue(refDurationMaster.getDurationCode());
		downDto.setValue(refDurationMaster.getName());
		return downDto;
		
	}
	//===================================ShapeType=========================================//
	
	@Override
	public List<CategoryShapeMaster> getAmeShapeType(String shapeTypeCode) {
	 List<CategoryShapeMaster>refShapes=refShapeRepo.findAll();
	 refShapes=refShapes.stream()
			 .filter(s->s.getTypeCode().equals(shapeTypeCode))
			 .collect(Collectors.toList());
	 return refShapes;
	 }


	
	
	//==============================AmeCategory===============================================//
	@Override
	public List<DropDownDto> getAmeCategoryType() {
		return categoryStatusTypeRepo.findAll()
				.stream()
				.map(AmeShapeCategoryDurationServiceImpl::getCategoryDropDown)
				.collect(Collectors.toList());
		}


	private static DropDownDto getCategoryDropDown(CategoryTypeMaster refcategorystatustype1) {
		DropDownDto downDto=new DropDownDto();
		//downDto.setId(refcategorystatustype1.getCodeName());
		downDto.setValue(refcategorystatustype1.getCodeName());
		return downDto;
	}
	
	
}
