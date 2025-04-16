package nic.ame.app.ama.service;

import java.io.File;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;

public interface SubordinateService_1 {

	

    public String uploadAmeDeclarationFile(MultipartFile file,String ameId,String candidateForcePersonalId,HttpServletRequest request);
    public String uploadAmeDeclarationFileAfterEsign(File file,String ameId,String candidateForcePersonalId,HttpServletRequest request);

    	

    
    
    public Optional<PsychologicalAssessmentAsLaidDown> assessmentAsLaidDown(String ameId);
	public boolean savePsychologicalAssessment(PsychologicalAssessmentAsLaidDown asLaidDown);
	public boolean updateAssessmentAsLaidDown(String ameId,PsychologicalAssessmentAsLaidDown asLaidDown);


}
