package nic.ame.app.master.model;

import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "tt_ame_declaration_individual_details")
public class AmeDeclarationIndividualDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "ame_id",nullable = false)
	private String ameId;
	
	@Column(name = "force_personal_id" ,nullable = false)
	private String forcePersonalId;
	
	/*@JsonFormat(pattern = "dd-MM-yyyy" ,shape = Shape.STRING)
	@Column(name = "declaration_date")
	private Date declarationDate;
	*/
	
	@Column(name ="major_ailment_hospitalized" )
	private String majorailmenthospitalized;
	
	@Column(name = "hypertension")
	private String hypertension;
	
	@Column(name = "ichaemic_heart")
	private String ichaemicheart;
	
	@Column(name = "diabetes_mellitus")
	private String diabetesmellitus;
	
	@Column(name = "copd")
	private String copd;
	
	@Column(name = "cp_date")
	private Date cpDate;
	
	@Column(name = "epilepsy")
	private String epilepsy;
	
	@Column(name = "persistent_headache")
	private String persistent_headache;
	
	@Column(name = "menatl_instability")
	private String menatlinstability;
	
	@Column(name = "giddiness")
	private String giddiness;
	
	@Column(name = "chest_pain_palpitation")
	private String chestpainpalpitation;
	
	@Column(name = "tb")
	private String tb;
	
	@Column(name = "sleep")
	private String sleep;
	
	private String appetite;
	
	@Column(name = "smoking_habit")
	private String smokinghabit;
	
	@Column(name = "number_of_cigarettes")
	private int numberofcigarettes;
	
	@Column(name = "alcohol_intake")
	private String alcoholintake;
	
	@Column(name = "alcohol_quantity_per_day")
	private int quantityperday;
	
	@Column(name = "accident_injury_surgery")
	private String accidentinjurysurgery;
	
	@Column(name = "defective_color_perception")
	private String defectivecolorperception;
	
	@Column(name = "color_perception_category")
	private String colorperceptioncat;
	
   @Column(name = "hiv")
	private String hivTest;
	
	@Column(name = "last_ame_status")
	private String lastAmeStatus;
	
	@Column(name = "recent_transfer_data")
	private String recentTransferData;
	
	
	
	
	
	
	
	//--------------------------------------------Getters and Setters--------------------------------------------------------//

	
	
	
	public String getRecentTransferData() {
		return recentTransferData;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRecentTransferData(String recentTransferData) {
		this.recentTransferData = recentTransferData;
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
/*
	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
*/
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

	public Date getCpDate() {
		return cpDate;
	}

	public void setCpDate(Date cpDate) {
		this.cpDate = cpDate;
	}

	public String getEpilepsy() {
		return epilepsy;
	}

	public void setEpilepsy(String epilepsy) {
		this.epilepsy = epilepsy;
	}

	public String getPersistent_headache() {
		return persistent_headache;
	}

	public void setPersistent_headache(String persistent_headache) {
		this.persistent_headache = persistent_headache;
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

	public String getAppetite() {
		return appetite;
	}

	public void setAppetite(String appetite) {
		this.appetite = appetite;
	}

	public String getSmokinghabit() {
		return smokinghabit;
	}

	public void setSmokinghabit(String smokinghabit) {
		this.smokinghabit = smokinghabit;
	}

	public int getNumberofcigarettes() {
		return numberofcigarettes;
	}

	public void setNumberofcigarettes(int numberofcigarettes) {
		this.numberofcigarettes = numberofcigarettes;
	}

	public String getAlcoholintake() {
		return alcoholintake;
	}

	public void setAlcoholintake(String alcoholintake) {
		this.alcoholintake = alcoholintake;
	}

	public int getQuantityperday() {
		return quantityperday;
	}

	public void setQuantityperday(int quantityperday) {
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

	public String getHivTest() {
		return hivTest;
	}

	public void setHivTest(String hivTest) {
		this.hivTest = hivTest;
	}

	public String getLastAmeStatus() {
		return lastAmeStatus;
	}

	public void setLastAmeStatus(String lastAmeStatus) {
		this.lastAmeStatus = lastAmeStatus;
	}
	
	
 
}

