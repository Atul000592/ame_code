package nic.ame.app.user.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "tt_feedback") // Ensure this matches your database table name
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(name = "force_id", nullable = false)
    private String irlaNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "designation", nullable = false)
    private String designation;
    
    @Column(name = "force_name", nullable = false)
    private String forceName;
    
    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "feedback", nullable = false, length = 500)
    private String feedback;

    @Column(name = "star_rating", nullable = false)
    private int starRating;
    
    @Column (name = "unit_id", nullable = false)
    private String unit;

    // Default constructor
    public FeedbackEntity() {
    }

    // Parameterized constructor
    public FeedbackEntity(String irlaNo, String name, String designation, String force_name, String unit_name, String feedback, int starRating, String unit_id) {
        this.irlaNo = irlaNo;
        this.name = name;
        this.designation = designation;
        this.forceName = forceName;
        this.unitName = unitName;
        this.feedback = feedback;
        this.starRating = starRating;
        this.unit = unit;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIrlaNo() {
        return irlaNo;
    }

    public void setIrlaNo(String irlaNo) {
        this.irlaNo = irlaNo;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }
    
    

    public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
    public String toString() {
        return "FeedbackEntity{" +
                "id=" + id +
                ", irlaNo='" + irlaNo + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", feedback='" + feedback + '\'' +
                ", starRating=" + starRating +
                '}';
    }
}
