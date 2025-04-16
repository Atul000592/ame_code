package nic.ame.app.master.ref.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class RefDropDownRange {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int lowerRange;
	private int upperRange;
	private String unit;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLowerRange() {
		return lowerRange;
	}
	public void setLowerRange(int lowerRange) {
		this.lowerRange = lowerRange;
	}
	public int getUpperRange() {
		return upperRange;
	}
	public void setUpperRange(int upperRange) {
		this.upperRange = upperRange;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
	
	
}
