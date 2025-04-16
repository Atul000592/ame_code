package nic.ame.app.master.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.PasswordResetToken;
import nic.ame.app.master.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	// Method to create a new password reset token
	public PasswordResetToken createPasswordResetToken(ForcePersonnel forcePersonal) {
		PasswordResetToken token = new PasswordResetToken();
		token.setForcePersonal(forcePersonal);
		token.setToken(generateToken());
		token.setExpiryDate(LocalDateTime.now().plusMinutes(15)); // Example: Token expires in 24 hours
		return tokenRepository.save(token);
	}

	// Method to find a token by its token string
	public PasswordResetToken findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	// Method to delete a token
	public void deleteToken(PasswordResetToken token) {
		tokenRepository.delete(token);
	}

	// Utility method to generate a unique token (example implementation)
	private String generateToken() {
	    Random random = new Random();
	    int token = 100000 + random.nextInt(900000); // Generate a number between 100000 and 999999
	    return String.valueOf(token);
	}
}