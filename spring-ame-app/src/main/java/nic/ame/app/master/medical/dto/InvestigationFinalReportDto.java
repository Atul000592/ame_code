package nic.ame.app.master.medical.dto;

import nic.ame.app.master.medical.model.BloodSugar;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;

public class InvestigationFinalReportDto {


	
	private LiverFunctionTest lft;
	private KidneyFunctionTest kft;
	private Lipid lipid;
	private CompleteBloodCount cbc;
	private UrineTestPhysicalMicroscopic utpm;
	private Others others ;
	private BloodSugar bs;
	
	
	public LiverFunctionTest getLft() {
		return lft;
	}
	public void setLft(LiverFunctionTest lft) {
		this.lft = lft;
	}
	public KidneyFunctionTest getKft() {
		return kft;
	}
	public void setKft(KidneyFunctionTest kft) {
		this.kft = kft;
	}
	public Lipid getLipid() {
		return lipid;
	}
	public void setLipid(Lipid lipid) {
		this.lipid = lipid;
	}
	public CompleteBloodCount getCbc() {
		return cbc;
	}
	public void setCbc(CompleteBloodCount cbc) {
		this.cbc = cbc;
	}
	public UrineTestPhysicalMicroscopic getUtpm() {
		return utpm;
	}
	public void setUtpm(UrineTestPhysicalMicroscopic utpm) {
		this.utpm = utpm;
	}
	public Others getOthers() {
		return others;
	}
	public void setOthers(Others others) {
		this.others = others;
	}
	public BloodSugar getBs() {
		return bs;
	}
	public void setBs(BloodSugar bs) {
		this.bs = bs;
	}
	@Override
	public String toString() {
		return "InvestigationFinalReportDto [lft=" + lft + ", kft=" + kft + ", lipid=" + lipid + ", cbc=" + cbc
				+ ", utpm=" + utpm + ", others=" + others + ", bs=" + bs + "]";
	}
	
	

}
