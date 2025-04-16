package nic.ame.app.master.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.model.ExaminationApplicationFlowHistory;
import nic.ame.app.master.repository.ExaminationApplicationFlowHistoryRepo;
import nic.ame.app.master.service.ExaminationApplicationFlowService;

@Service
public class ExaminationApplicationFlowServiceImpl implements ExaminationApplicationFlowService{
	
	
	@Autowired
	private ExaminationApplicationFlowHistoryRepo ExaminationApplicationFlowHistoryRepo;

	@Override
	public ExaminationApplicationFlowHistory saveHistory(
			ExaminationApplicationFlowHistory examinationApplicationFlowHistory) {
	
		return this.ExaminationApplicationFlowHistoryRepo.save(examinationApplicationFlowHistory);
	}

}
