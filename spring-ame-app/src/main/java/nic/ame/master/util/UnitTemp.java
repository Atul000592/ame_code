package nic.ame.master.util;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="unit_temp")

public class UnitTemp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "unit_name")
	private String unitName;

	@Column(name = "force_no")
	private int forceNo;
	
	@Column(name = "unit_id")
	private  String unit_id;
}
