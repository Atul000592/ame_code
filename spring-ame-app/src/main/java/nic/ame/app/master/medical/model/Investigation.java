package nic.ame.app.master.medical.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Table(name = "medical_examination_investigation")
@Entity
public class Investigation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ame_id")
	private String ameId;
	private String hb;
	private String urine;

	private String microscope_ex;
	private String blood_sugar;
	private String blood_sugar_pp;
	private String glycosylated_hb;
	private String s_cholesterol;
	private String hdl;
	private String ratio;
	private String triglyceride;
	private String ldl;
	private String vLDL;
	private String blood_urea;
	private String s_creatinine;
	private String uric_acid;
	private String xRay_chest;
	private String eCG;
	private String tMT;
	private String echo_cardiography;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getHb() {
		return hb;
	}
	public void setHb(String hb) {
		this.hb = hb;
	}
	public String getUrine() {
		return urine;
	}
	public void setUrine(String urine) {
		this.urine = urine;
	}
	
	public String getMicroscope_ex() {
		return microscope_ex;
	}
	public void setMicroscope_ex(String microscope_ex) {
		this.microscope_ex = microscope_ex;
	}
	public String getBlood_sugar() {
		return blood_sugar;
	}
	public void setBlood_sugar(String blood_sugar) {
		this.blood_sugar = blood_sugar;
	}
	public String getBlood_sugar_pp() {
		return blood_sugar_pp;
	}
	public void setBlood_sugar_pp(String blood_sugar_pp) {
		this.blood_sugar_pp = blood_sugar_pp;
	}
	public String getGlycosylated_hb() {
		return glycosylated_hb;
	}
	public void setGlycosylated_hb(String glycosylated_hb) {
		this.glycosylated_hb = glycosylated_hb;
	}
	
	public String getS_cholesterol() {
		return s_cholesterol;
	}
	public void setS_cholesterol(String s_cholesterol) {
		this.s_cholesterol = s_cholesterol;
	}
	public String getHdl() {
		return hdl;
	}
	public void setHdl(String hdl) {
		this.hdl = hdl;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getTriglyceride() {
		return triglyceride;
	}
	public void setTriglyceride(String triglyceride) {
		this.triglyceride = triglyceride;
	}
	public String getLdl() {
		return ldl;
	}
	public void setLdl(String ldl) {
		this.ldl = ldl;
	}
	public String getvLDL() {
		return vLDL;
	}
	public void setvLDL(String vLDL) {
		this.vLDL = vLDL;
	}
	public String getBlood_urea() {
		return blood_urea;
	}
	public void setBlood_urea(String blood_urea) {
		this.blood_urea = blood_urea;
	}
	public String getS_creatinine() {
		return s_creatinine;
	}
	public void setS_creatinine(String s_creatinine) {
		this.s_creatinine = s_creatinine;
	}
	public String getUric_acid() {
		return uric_acid;
	}
	public void setUric_acid(String uric_acid) {
		this.uric_acid = uric_acid;
	}
	public String getxRay_chest() {
		return xRay_chest;
	}
	public void setxRay_chest(String xRay_chest) {
		this.xRay_chest = xRay_chest;
	}
	public String geteCG() {
		return eCG;
	}
	public void seteCG(String eCG) {
		this.eCG = eCG;
	}
	public String gettMT() {
		return tMT;
	}
	public void settMT(String tMT) {
		this.tMT = tMT;
	}
	public String getEcho_cardiography() {
		return echo_cardiography;
	}
	public void setEcho_cardiography(String echo_cardiography) {
		this.echo_cardiography = echo_cardiography;
	}
	
}
