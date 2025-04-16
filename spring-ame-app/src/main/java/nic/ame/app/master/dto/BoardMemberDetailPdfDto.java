package nic.ame.app.master.dto;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Id;

public class BoardMemberDetailPdfDto {
	

		
		private String forcePersonalId;

		//  forceId = IRLA number
		private String forceId;

		private String forceName;
		
		private String name;
		
		private Integer forceNo;
		
		private String rank;
		
		

		private String designation;

		private String unit;
		
		private String unitName;
		
		
		public String getForcePersonalId() {
			return forcePersonalId;
		}

		public void setForcePersonalId(String forcePersonalId) {
			this.forcePersonalId = forcePersonalId;
		}

		public String getForceId() {
			return forceId;
		}

		public void setForceId(String forceId) {
			this.forceId = forceId;
		}

		public String getForceName() {
			return forceName;
		}

		public void setForceName(String forceName) {
			this.forceName = forceName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getForceNo() {
			return forceNo;
		}

		public void setForceNo(Integer forceNo) {
			this.forceNo = forceNo;
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

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}

	
		
}

