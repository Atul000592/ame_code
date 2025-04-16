package nic.ame.app.master.model;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Column;

public class AmeDeclarationDtoResponse {
	private String declarationDate;

	private String declarationCode;

	private String declarationFlag;

	private String declarationSubtypeFlag;
	private String place;

	private int sNumber;

	private String selfCertifyAboveInfo;

	@Column(name = "last_ame_date")
	private Date lastAmeDate;
	
	private String lastAmePlace;

	private String name;

	private String remark;

	//private BigInteger rank;
    //private BigInteger designation;
    
	private String rank;
    private String designation;
	private String unit;
    private String declarationSubtypeCode;
    private String ameId;
    private String forcePersonalId;
	private Integer forceNumber;
    private String majorailmenthospitalized;
	private String hypertension;
	private String ichaemicheart;
	private String diabetesmellitus;
	private String copd;
	private String epilepsy;
	private String persistentheadache;
	private String menatlinstability;
	private String giddiness;
	private String chestpainpalpitation;
	private String tb;
	private String sleep;
	private String smokinghabit;
	private String numberofcigarettes;
	private String alcoholintake;
	private String quantityperday;
	private String accidentinjurysurgery;
	private String defectivecolorperception;
	private String colorperceptioncat;
	private String declarationcode;
	private String declarationdate;
	private String curentnewunit;
	private String previousunit;
	private String forcepersonlId;
	private String forceId;
	private String declarationflag;
	private String declarationsubtypeflag;
	private String dcpdate;
	private String hivTest;
	private String certified_name;
	private String stateId_last_ame;
	private String districtId_last_ame;
	private String lastAmeStatus;
	public String getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(String declarationDate) {
		this.declarationDate = declarationDate;
	}
	public String getDeclarationCode() {
		return declarationCode;
	}
	public void setDeclarationCode(String declarationCode) {
		this.declarationCode = declarationCode;
	}
	public String getDeclarationFlag() {
		return declarationFlag;
	}
	public void setDeclarationFlag(String declarationFlag) {
		this.declarationFlag = declarationFlag;
	}
	public String getDeclarationSubtypeFlag() {
		return declarationSubtypeFlag;
	}
	public void setDeclarationSubtypeFlag(String declarationSubtypeFlag) {
		this.declarationSubtypeFlag = declarationSubtypeFlag;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getsNumber() {
		return sNumber;
	}
	public void setsNumber(int sNumber) {
		this.sNumber = sNumber;
	}
	public String getSelfCertifyAboveInfo() {
		return selfCertifyAboveInfo;
	}
	public void setSelfCertifyAboveInfo(String selfCertifyAboveInfo) {
		this.selfCertifyAboveInfo = selfCertifyAboveInfo;
	}
	public Date getLastAmeDate() {
		return lastAmeDate;
	}
	public void setLastAmeDate(Date lastAmeDate) {
		this.lastAmeDate = lastAmeDate;
	}
	public String getLastAmePlace() {
		return lastAmePlace;
	}
	public void setLastAmePlace(String lastAmePlace) {
		this.lastAmePlace = lastAmePlace;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDeclarationSubtypeCode() {
		return declarationSubtypeCode;
	}
	public void setDeclarationSubtypeCode(String declarationSubtypeCode) {
		this.declarationSubtypeCode = declarationSubtypeCode;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getForcePersonalId() {
		return forcePersonalId;
	}
	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}
	public Integer getForceNumber() {
		return forceNumber;
	}
	public void setForceNumber(Integer forceNumber) {
		this.forceNumber = forceNumber;
	}
	public String getMajorailmenthospitalized() {
		return majorailmenthospitalized;
	}
	public void setMajorailmenthospitalized(String majorailmenthospitalized) {
		this.majorailmenthospitalized = majorailmenthospitalized;
	}
	public String getHypertension() {
		return hypertension;
	}
	public void setHypertension(String hypertension) {
		this.hypertension = hypertension;
	}
	public String getIchaemicheart() {
		return ichaemicheart;
	}
	public void setIchaemicheart(String ichaemicheart) {
		this.ichaemicheart = ichaemicheart;
	}
	public String getDiabetesmellitus() {
		return diabetesmellitus;
	}
	public void setDiabetesmellitus(String diabetesmellitus) {
		this.diabetesmellitus = diabetesmellitus;
	}
	public String getCopd() {
		return copd;
	}
	public void setCopd(String copd) {
		this.copd = copd;
	}
	public String getEpilepsy() {
		return epilepsy;
	}
	public void setEpilepsy(String epilepsy) {
		this.epilepsy = epilepsy;
	}
	public String getPersistentheadache() {
		return persistentheadache;
	}
	public void setPersistentheadache(String persistentheadache) {
		this.persistentheadache = persistentheadache;
	}
	public String getMenatlinstability() {
		return menatlinstability;
	}
	public void setMenatlinstability(String menatlinstability) {
		this.menatlinstability = menatlinstability;
	}
	public String getGiddiness() {
		return giddiness;
	}
	public void setGiddiness(String giddiness) {
		this.giddiness = giddiness;
	}
	public String getChestpainpalpitation() {
		return chestpainpalpitation;
	}
	public void setChestpainpalpitation(String chestpainpalpitation) {
		this.chestpainpalpitation = chestpainpalpitation;
	}
	public String getTb() {
		return tb;
	}
	public void setTb(String tb) {
		this.tb = tb;
	}
	public String getSleep() {
		return sleep;
	}
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}
	public String getSmokinghabit() {
		return smokinghabit;
	}
	public void setSmokinghabit(String smokinghabit) {
		this.smokinghabit = smokinghabit;
	}
	public String getNumberofcigarettes() {
		return numberofcigarettes;
	}
	public void setNumberofcigarettes(String numberofcigarettes) {
		this.numberofcigarettes = numberofcigarettes;
	}
	public String getAlcoholintake() {
		return alcoholintake;
	}
	public void setAlcoholintake(String alcoholintake) {
		this.alcoholintake = alcoholintake;
	}
	public String getQuantityperday() {
		return quantityperday;
	}
	public void setQuantityperday(String quantityperday) {
		this.quantityperday = quantityperday;
	}
	public String getAccidentinjurysurgery() {
		return accidentinjurysurgery;
	}
	public void setAccidentinjurysurgery(String accidentinjurysurgery) {
		this.accidentinjurysurgery = accidentinjurysurgery;
	}
	public String getDefectivecolorperception() {
		return defectivecolorperception;
	}
	public void setDefectivecolorperception(String defectivecolorperception) {
		this.defectivecolorperception = defectivecolorperception;
	}
	public String getColorperceptioncat() {
		return colorperceptioncat;
	}
	public void setColorperceptioncat(String colorperceptioncat) {
		this.colorperceptioncat = colorperceptioncat;
	}
	public String getDeclarationcode() {
		return declarationcode;
	}
	public void setDeclarationcode(String declarationcode) {
		this.declarationcode = declarationcode;
	}
	public String getDeclarationdate() {
		return declarationdate;
	}
	public void setDeclarationdate(String declarationdate) {
		this.declarationdate = declarationdate;
	}
	public String getCurentnewunit() {
		return curentnewunit;
	}
	public void setCurentnewunit(String curentnewunit) {
		this.curentnewunit = curentnewunit;
	}
	public String getPreviousunit() {
		return previousunit;
	}
	public void setPreviousunit(String previousunit) {
		this.previousunit = previousunit;
	}
	public String getForcepersonlId() {
		return forcepersonlId;
	}
	public void setForcepersonlId(String forcepersonlId) {
		this.forcepersonlId = forcepersonlId;
	}
	public String getForceId() {
		return forceId;
	}
	public void setForceId(String forceId) {
		this.forceId = forceId;
	}
	public String getDeclarationflag() {
		return declarationflag;
	}
	public void setDeclarationflag(String declarationflag) {
		this.declarationflag = declarationflag;
	}
	public String getDeclarationsubtypeflag() {
		return declarationsubtypeflag;
	}
	public void setDeclarationsubtypeflag(String declarationsubtypeflag) {
		this.declarationsubtypeflag = declarationsubtypeflag;
	}
	public String getDcpdate() {
		return dcpdate;
	}
	public void setDcpdate(String dcpdate) {
		this.dcpdate = dcpdate;
	}
	public String getHivTest() {
		return hivTest;
	}
	public void setHivTest(String hivTest) {
		this.hivTest = hivTest;
	}
	public String getCertified_name() {
		return certified_name;
	}
	public void setCertified_name(String certified_name) {
		this.certified_name = certified_name;
	}
	public String getStateId_last_ame() {
		return stateId_last_ame;
	}
	public void setStateId_last_ame(String stateId_last_ame) {
		this.stateId_last_ame = stateId_last_ame;
	}
	public String getDistrictId_last_ame() {
		return districtId_last_ame;
	}
	public void setDistrictId_last_ame(String districtId_last_ame) {
		this.districtId_last_ame = districtId_last_ame;
	}
	public String getLastAmeStatus() {
		return lastAmeStatus;
	}
	public void setLastAmeStatus(String lastAmeStatus) {
		this.lastAmeStatus = lastAmeStatus;
	}
	
	
	
	
	
	
	
	}
