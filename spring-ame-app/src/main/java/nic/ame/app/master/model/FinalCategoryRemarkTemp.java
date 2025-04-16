package nic.ame.app.master.model;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class FinalCategoryRemarkTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String ameId;
	private String categoryType;
	private String categoryTypeName;
	private String subCategoryType;
	@ColumnDefault(value ="0")
	private int status;

	private String comment;

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

	public String getCategoryType() {
		return categoryType; 
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getSubCategoryType() {
		return subCategoryType;
	}

	public void setSubCategoryType(String subCategoryType) {
		this.subCategoryType = subCategoryType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCategoryTypeName() {
		return categoryTypeName;
	}

	public void setCategoryTypeName(String categoryTypeName) {
		this.categoryTypeName = categoryTypeName;
	}

	@Override
	public String toString() {
		return "FinalCategoryRemarkTemp [id=" + id + ", ameId=" + ameId + ", categoryType=" + categoryType
				+ ", categoryTypeName=" + categoryTypeName + ", subCategoryType=" + subCategoryType + ", comment="
				+ comment + "]";
	}

	
	
	
	
	
	
	
	
}
