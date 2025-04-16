package nic.ame.app.master.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;

public class AmeDeclarationDtoRequest {

	private Date declarationDate;

	private String declarationCode;

	private String declarationFlag;

	private String declarationSubtypeFlag;

	private String ameId;

	private String forceNumber;

	private String forcePersonalId;
	
	private String transferredid;

	private String color_perception_cat;
	
	private String lastAmePlace;

	private int stateLgdCode;

	private int districtLgdCode;

	@Column(name = "place")
	private String place;

	@Column(name = "s_no")
	private int sNumber;

	private String selfCertifyAboveInfo;

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

	private String appetite;

	private String dcpdate;

	private String certified_name;

	private String forceId;

	private String hivTest;
	
	private int stateId_last_ame;
	
	private int districtId_last_ame;
	
	private Date defectivecolorperception_date;
	
	private String lastAmeStatus;
	
	private String boardId;
	
	//@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
    private String lastAmeDate;
	
	

	public String getLastAmeStatus() {
		return lastAmeStatus;
	}

	public void setLastAmeStatus(String lastAmeStatus) {
		this.lastAmeStatus = lastAmeStatus;
	}

	public String getTransferredid() {
		return transferredid;
	}

	public void setTransferredid(String transferredid) {
		this.transferredid = transferredid;
	}
	public Date getDefectivecolorperception_date() {
		return defectivecolorperception_date;
	}

	public void setDefectivecolorperception_date(Date defectivecolorperception_date) {
		this.defectivecolorperception_date = defectivecolorperception_date;
	}

	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
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

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}

	public String getForceNumber() {
		return forceNumber;
	}

	public void setForceNumber(String forceNumber) {
		this.forceNumber = forceNumber;
	}

	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}

	public String getLastAmePlace() {
		return lastAmePlace;
	}

	public void setLastAmePlace(String lastAmePlace) {
		this.lastAmePlace = lastAmePlace;
	}

	public int getStateLgdCode() {
		return stateLgdCode;
	}

	public void setStateLgdCode(int stateLgdCode) {
		this.stateLgdCode = stateLgdCode;
	}

	public int getDistrictLgdCode() {
		return districtLgdCode;
	}

	public void setDistrictLgdCode(int districtLgdCode) {
		this.districtLgdCode = districtLgdCode;
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

	public String getAppetite() {
		return appetite;
	}

	public void setAppetite(String appetite) {
		this.appetite = appetite;
	}

	public String getDcpdate() {
		return dcpdate;
	}

	public void setDcpdate(String dcpdate) {
		this.dcpdate = dcpdate;
	}

	public String getCertified_name() {
		return certified_name;
	}

	public void setCertified_name(String certified_name) {
		this.certified_name = certified_name;
	}

	public String getForceId() {
		return forceId;
	}

	public void setForceId(String forceId) {
		this.forceId = forceId;
	}

	public String getHivTest() {
		return hivTest;
	}

	public void setHivTest(String hivTest) {
		this.hivTest = hivTest;
	}

	public int getStateId_last_ame() {
		return stateId_last_ame;
	}

	public void setStateId_last_ame(int stateId_last_ame) {
		this.stateId_last_ame = stateId_last_ame;
	}

	public int getDistrictId_last_ame() {
		return districtId_last_ame;
	}

	public void setDistrictId_last_ame(int districtId_last_ame) {
		this.districtId_last_ame = districtId_last_ame;
	}

	public String getColor_perception_cat() {
		return color_perception_cat;
	}

	public void setColor_perception_cat(String color_perception_cat) {
		this.color_perception_cat = color_perception_cat;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getLastAmeDate() {
		return lastAmeDate;
	}

	public void setLastAmeDate(String lastAmeDate) {
		this.lastAmeDate = lastAmeDate;
	}

	
	
	
	

}
