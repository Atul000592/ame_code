package nic.ame.app.admin.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table
public class RefMedicalExamType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String examName;

	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}

	
}
