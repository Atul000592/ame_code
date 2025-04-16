package nic.ame.app.master.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(targetEntity = ForcePersonnel.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "force_id")
	private ForcePersonnel forcePersonal;

	private String token;

	private LocalDateTime expiryDate;

	// Constructors, getters, and setters
	public PasswordResetToken() {
	}

	public PasswordResetToken(ForcePersonnel forcePersonal, String token, LocalDateTime expiryDate) {
		// this.forcePersonal = forcePersonal;
		this.token = token;
		this.expiryDate = expiryDate;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public ForcePersonnel getForcePersonal() {
		return forcePersonal;
	}

	public void setForcePersonal(ForcePersonnel forcePersonal) {
		this.forcePersonal = forcePersonal;
	}

}
