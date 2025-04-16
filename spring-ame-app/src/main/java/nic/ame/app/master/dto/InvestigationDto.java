package nic.ame.app.master.dto;

import java.util.List;

import lombok.Data;
import nic.ame.app.master.medical.model.BloodSugar;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.OtherTest;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.ThyroidProfile;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;
import nic.ame.app.master.medical.model.ViralMakers;


public class InvestigationDto {

    private LiverFunctionTest liverFunctionTest;
    
    private KidneyFunctionTest kft;

    private ThyroidProfile thyroidProfile;

    private ViralMakers viralMakers;

    private OtherTest otherTest;

    private Lipid lipid;

    private CompleteBloodCount completeBloodCountCBC;

    private BloodSugar bloodSugar;
    
    private BloodSugarPP bloodSugarPPValue;
    
    private BloodSugarF bloodSugarFValue;
    
    private BloodSugarRandom bloodSugarRandomValue;
    
    private BloodSugarHbA1c bloodSugarHbA1cValue;

    private UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic;

    private List<Others> others;

    

   
    public LiverFunctionTest getLiverFunctionTest() {
        return liverFunctionTest;
    }

    public void setLiverFunctionTest(LiverFunctionTest liverFunctionTest) {
        this.liverFunctionTest = liverFunctionTest;
    }

    public KidneyFunctionTest getKft() {
        return kft;
    }

    public void setKft(KidneyFunctionTest kft) {
        this.kft = kft;
    }

    public ThyroidProfile getThyroidProfile() {
        return thyroidProfile;
    }

    public void setThyroidProfile(ThyroidProfile thyroidProfile) {
        this.thyroidProfile = thyroidProfile;
    }

    public ViralMakers getViralMakers() {
        return viralMakers;
    }

    public void setViralMakers(ViralMakers viralMakers) {
        this.viralMakers = viralMakers;
    }

    public OtherTest getOtherTest() {
        return otherTest;
    }

    public void setOtherTest(OtherTest otherTest) {
        this.otherTest = otherTest;
    }

    public Lipid getLipid() {
        return lipid;
    }

    public void setLipid(Lipid lipid) {
        this.lipid = lipid;
    }

    public CompleteBloodCount getCompleteBloodCountCBC() {
        return completeBloodCountCBC;
    }

    public void setCompleteBloodCountCBC(CompleteBloodCount completeBloodCountCBC) {
        this.completeBloodCountCBC = completeBloodCountCBC;
    }

    public BloodSugar getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(BloodSugar bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public UrineTestPhysicalMicroscopic getUrineTestPhysicalMicroscopic() {
        return urineTestPhysicalMicroscopic;
    }

    public void setUrineTestPhysicalMicroscopic(UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic) {
        this.urineTestPhysicalMicroscopic = urineTestPhysicalMicroscopic;
    }

    public List<Others> getOthers() {
        return others;
    }

    public void setOthers(List<Others> others) {
        this.others = others;
    }

	public BloodSugarPP getBloodSugarPPValue() {
		return bloodSugarPPValue;
	}

	public void setBloodSugarPPValue(BloodSugarPP bloodSugarPPValue) {
		this.bloodSugarPPValue = bloodSugarPPValue;
	}

	public BloodSugarF getBloodSugarFValue() {
		return bloodSugarFValue;
	}

	public void setBloodSugarFValue(BloodSugarF bloodSugarFValue) {
		this.bloodSugarFValue = bloodSugarFValue;
	}

	public BloodSugarRandom getBloodSugarRandomValue() {
		return bloodSugarRandomValue;
	}

	public void setBloodSugarRandomValue(BloodSugarRandom bloodSugarRandomValue) {
		this.bloodSugarRandomValue = bloodSugarRandomValue;
	}

	public BloodSugarHbA1c getBloodSugarHbA1cValue() {
		return bloodSugarHbA1cValue;
	}

	public void setBloodSugarHbA1cValue(BloodSugarHbA1c bloodSugarHbA1cValue) {
		this.bloodSugarHbA1cValue = bloodSugarHbA1cValue;
	}

	
    

    // KidneyFunctionTestKFT kft =new kidneyFunctionTestKFT();
    // kft= findByAmeId(amdid);
    // InvestigationDto iDto=new InvestigationDto();
    // idto.set


    
}
