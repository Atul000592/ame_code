package com.nic.in.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nic.in.Repository.DesignationMasterRepository;
import com.nic.in.modal.DesignationMaster;
import com.nic.in.service.DesignationMasterService;

@Service
public class DesignationMasterServiceImpl implements DesignationMasterService {
	
	@Autowired private DesignationMasterRepository designationMasterRepository;

	@Override
	public List<DesignationMaster> getDesigList() {
		// TODO Auto-generated method stub
		return designationMasterRepository.findAll();
	}

}
