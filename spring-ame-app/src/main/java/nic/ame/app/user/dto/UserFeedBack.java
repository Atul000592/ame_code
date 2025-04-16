package nic.ame.app.user.dto;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tt_feedback")
public class UserFeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "force_id")
    private String irlaNo;

    private String name;

    private String designation;
    
    @Column(name = "force_name")
    private String forceName;

    private String feedback;

    @Column(name = "star_rating")
    private Integer starRating;

    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;
    
    @Column(name = "force_no")
    private Integer forceNo;

    @Column(name = "unit_id")
    private String unit;


    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
    }

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
	
	public Integer getStarRating() {
		return starRating;
	}

	public void setStarRating(Integer starRating) {
		this.starRating = starRating;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public void setForceid(String irlaNo) {
		// TODO Auto-generated method stub
		
	}

	public String getForceName() {
		return forceName;
	}

	public void setForceName(String forceName) {
		this.forceName = forceName;
	}

	@Override
	public String toString() {
		return "Userfeedback [id=" + id + ", irlaNo=" + irlaNo + ", name=" + name + ", designation=" + designation
				+ ", forceName=" + forceName + ", feedback=" + feedback + ", starRating=" + starRating + ", createdOn="
				+ createdOn + "]";
	}

	public void setUnitName(Object getunitName) {
		// TODO Auto-generated method stub
		
	}

	public Integer getForceNo() {
		return forceNo;
	}

	public void setForceNo(Integer forceNo) {
		this.forceNo = forceNo;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	

}

