package nic.ame.app.master.controller;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import nic.ame.app.email.EmailService;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;
import nic.ame.app.master.model.PasswordResetToken;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.repository.PasswordResetTokenRepository;
import nic.ame.app.master.service.DecryptAESServiceForPasswordEncryption;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.PasswordResetTokenService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.EmailSendUtil;
import nic.ame.master.util.HexStringToByteArray;
import nic.ame.master.util.SHA256WithSalt;
import nic.ame.master.util.SHA256WithSaltNew;

@Controller
@Slf4j
public class PasswordController {

	
	Logger logger=LoggerFactory.getLogger(PasswordController.class);
	
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository; 
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private ForcePersonalService forcePersonalService;
	
	
	@Autowired
	private PasswordResetTokenService tokenService;
	
	@Autowired
	private DecryptAESServiceForPasswordEncryption aesServiceForPasswordEncryption;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private EmailService emailService;

	@RequestMapping(value = "forgot-password", method = RequestMethod.GET)
	public String showForgotPasswordForm(Model model, HttpSession httpSession) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		return "base-template/forgot-password";
	}

	@PostMapping("/forgot-password")
	public String processForgotPasswordForm(@RequestParam("forceId") String forceId, HttpServletRequest request,
			Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) throws Exception {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		ForcePersonnel forcePersonnel = forcePersonalService.findByForceId(forceId);
		if (forcePersonnel == null) {
			// Handle case where user is not found
			return "redirect:/forgot-password?error";
		}

		String irlaNumber = forcePersonnel.getForceId();
		// Create a password reset token
		if (irlaNumber != null && !irlaNumber.isEmpty()) {
			PasswordResetToken token = tokenService.createPasswordResetToken(forcePersonnel);

			Long id = token.getId();

			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + id);

			String resetLink = "https://your-domain.com/reset-password?token=" + token.getToken();
			String recipientEmail = forcePersonnel.getEmailId();
			logger.info("recipientEmail"+recipientEmail);
			if (recipientEmail == null) {
			    
				recipientEmail = "atul29030@gmail.com";
			}

			String subject = "Forgot Password OTP";
			String content = "<html><body>" +
					"<p>Dear Sir/Mam,</p>" +
					"<p><strong>" + String.valueOf(token.getToken()) + "</strong> is your OTP for the AME login password resetting in case of forgotten. The OTP is valid for 10 minutes only.</p>"
					+
					"<p>This is a system-generated email. Please do not reply.</p>" +
					"<p>Best Regards</p>" +
					"<p>AME Support Team</p>" +
					"</body></html>";

			
			logger.info("before sending the mail");
			
			// Send the email using EmailService
			boolean success = emailService.sendEmailWithSinglerecipent(
					recipientEmail,
					subject,
					content);
		//	EmailSendUtil.sendmail(String.valueOf(token.getToken()), "otp for password", null);
             
			logger.info("Mail send Status >>>>>>>>>>>>>>"+success);
			redirectAttributes.addFlashAttribute("id", id);

			return "redirect:/reset-password";
		} else {
			return "redirect:/forgot-password?error=irla_not_found";
		}
	}

	@GetMapping("/reset-password")
	public String showResetPasswordForm(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession)
			throws Exception {

		Long id = null;
		
		String errorMessage=null;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			id = (Long) inputFlashMap.get("id");
			errorMessage=(String) inputFlashMap.get("error_message");
		}
		Optional<PasswordResetToken> optPasswordResetToken = this.passwordResetTokenRepository.findById(id);
		if (!optPasswordResetToken.isPresent()) {
			throw new Exception("Data Error : Token is not present");
		}
		PasswordResetToken passwordResetToken = optPasswordResetToken.get();
		model.addAttribute("token", passwordResetToken);
		String secretKey = HexStringToByteArray.keyGeneraterHex(32);
		String ivParam = HexStringToByteArray.keyGeneraterHex(16);
		model.addAttribute("hexKeytoken", secretKey);
		model.addAttribute("ivHextoken", ivParam);
		model.addAttribute("error_message", errorMessage);
		httpSession.setAttribute("hexKeytoken", secretKey);
		httpSession.setAttribute("ivHextoken", ivParam);

		return "base-template/reset-new-password";
	}
	
	
	
	@RequestMapping(path = "/save-reset-password-agian" ,method = RequestMethod.POST)
	public String saveResetPassword(@RequestParam("forceId") String forceId,
			@RequestParam("reTypePassword") String reTypePassword,
			@RequestParam("password") String password,
			@RequestParam("otp") String otp,
			@RequestParam("id") Long id,
			@RequestParam("captcha") String captcha,
			HttpServletRequest httpServletRequest,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		
		String decryptPassword=null;
		String decryptReTypePassword=null;
		HttpSession httpSession= httpServletRequest.getSession();
		String hexKeytoken=(String) httpSession.getAttribute("hexKeytoken");
		String ivHextoken=(String) httpSession.getAttribute("ivHextoken");
		
		String storedCaptcha = (String) httpSession.getAttribute("captcha");
		if (storedCaptcha != null && storedCaptcha.equalsIgnoreCase(captcha)) {
			
			Optional<PasswordResetToken> optpasswordResetToken=this.passwordResetTokenRepository.findById(id);
			if(optpasswordResetToken.isPresent()) {
				if(!optpasswordResetToken.get().getToken().equals(otp)) {
					redirectAttributes.addFlashAttribute("id", id);
					redirectAttributes.addFlashAttribute("error_message", "Invalid Otp");
			        return "redirect:/reset-password";
				}
			
		
		if(password!=null) {
			decryptPassword=aesServiceForPasswordEncryption.getDecryptAESPassword(password, ivHextoken, hexKeytoken);
		}
		
		if(reTypePassword!=null) {
		 decryptReTypePassword=	aesServiceForPasswordEncryption.getDecryptAESPassword(reTypePassword, ivHextoken, hexKeytoken);
		}
		decryptPassword= SHA256WithSaltNew.sha256WithSalt(decryptPassword, CommonConstant.SHA_KEY_256);
		decryptReTypePassword= SHA256WithSaltNew.sha256WithSalt(decryptReTypePassword, CommonConstant.SHA_KEY_256);
		Optional<Login> login =this.loginRepository.findByUserName(forceId);
		if(!login.isPresent()) {
			
		}
		Login loginDetails = login.get();
		String p1 = loginDetails.getPassword();
		String p2 = loginDetails.getPassword1();
		String p3 = loginDetails.getPassword2();

		 if (decryptPassword.equals(decryptReTypePassword)) {
		        // Check if p1 is null and update it
		        if (p1 == null) {
		            loginDetails.setPassword(bCryptPasswordEncoder.encode(decryptReTypePassword));
		            loginRepository.save(loginDetails);
		            return "redirect:/login?resetSuccess"; // Redirect to a success page
		        } else {
		            // Check if decryptPassword matches p1
		            if (bCryptPasswordEncoder.matches(decryptPassword, p1)) {
		                redirectAttributes.addFlashAttribute("id", id);
		                redirectAttributes.addFlashAttribute("error_message", "New password cannot be the same as the previous password.");
		                return "redirect:/reset-password";
		            } else {
		                // Check if p2 is null and update it
		                if (p2 == null) {
		                    loginDetails.setPassword(bCryptPasswordEncoder.encode(decryptPassword));
		                    loginRepository.save(loginDetails);
		                    return "redirect:/login?resetSuccess";
		                } else {
		                    // Check if decryptPassword matches p2
		                    if (bCryptPasswordEncoder.matches(decryptPassword, p2)) {
		                        redirectAttributes.addFlashAttribute("id", id);
		                        redirectAttributes.addFlashAttribute("error_message", "New password cannot be the same as the previous password.");

		                        return "redirect:/reset-password";
		                    } else {
		                        // Check if p3 is null and update it
		                        if (p3 == null) {
		                            loginDetails.setPassword(bCryptPasswordEncoder.encode(decryptPassword));
		                            loginDetails.setPassword1(p1);
		                            loginRepository.save(loginDetails);
		                            return "redirect:/login?resetSuccess";
		                        } else {
		                            // Check if decryptPassword matches p3
		                            if (bCryptPasswordEncoder.matches(decryptPassword, p3)) {
		                                redirectAttributes.addFlashAttribute("id", id);
		                                redirectAttributes.addFlashAttribute("error_message", "New password cannot be the same as a previous password.");
		                                return "redirect:/reset-password";
		                            } else {
		                                // All password slots are filled, handle accordingly
		                            	  loginDetails.setPassword(bCryptPasswordEncoder.encode(decryptPassword));
				                            loginDetails.setPassword1(p1);
				                            loginDetails.setPassword2(p2);
				                            loginRepository.save(loginDetails);
				                            return "redirect:/login?resetSuccess";
		                            }
		                        }
		                    }
		                }
		            }
		        }
		    } else {
		        // Passwords do not match
		    	redirectAttributes.addFlashAttribute("id", id);
		    	 redirectAttributes.addFlashAttribute("error_message", "Passwords do not match.");
		        return "redirect:/reset-password";
		    }
		 
		 
	
	}
		else {
			redirectAttributes.addFlashAttribute("id", id);
			redirectAttributes.addFlashAttribute("error_message", "Invalid Otp");
	        return "redirect:/reset-password";
		}
		}
			else {
		
		redirectAttributes.addFlashAttribute("id", id);
		redirectAttributes.addFlashAttribute("error_message", "Invalid Captcha");
        return "redirect:/reset-password";
	}

	}
	
	
	@PostMapping("validate-and-change-password")
	public ResponseEntity<?> validateAndchangePassword(@RequestBody Map<String,String> data,CsrfToken csrfToken,HttpServletRequest httpServletRequest, HttpServletResponse httpResponse ) { String
	  regex="^(?=.*[@#$%^&*])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";
	String loginForcePersonalId=null; String oldPassword=null; 
	String oldPasswordBcrypt=null; 
	String newPassword=null;
	String confirmPassword=null;
	String password=null;
	String password1=null; 
	String password2=null; 
	Boolean result; // String confirmPasswordErrors=""; // String newPasswordErrors="";
	  System.out.println("Recieved Data: " + data);
	  System.out.println("CSRF TOKEN: " + csrfToken);
	  
	  Map<String, Object> response = new HashMap<>(); Map<String, String> errors =
	  new HashMap();
	  
	  try { 
		  for (Map.Entry<String, String> entry : data.entrySet())
	  { 
			  if
	  (entry.getKey().equals("loginForcePersonalId") && !entry.getKey().isEmpty()
	  && !entry.getKey().isBlank()) { loginForcePersonalId =
	  entry.getValue().trim(); }
	  
	  if (entry.getKey().equals("oldPassword") && !entry.getKey().isEmpty() &&
	  !entry.getKey().isBlank()) {
		  oldPassword = entry.getValue().trim(); }
	  
	  if (entry.getKey().equals("newPassword") && entry.getValue() != null) {
	  newPassword = entry.getValue().trim(); }
	  
	  if (entry.getKey().equals("confirmPassword") && entry.getValue() != null) {
	  confirmPassword = entry.getValue().trim(); }
	  
	  
	  }
		  HttpSession httpSession= httpServletRequest.getSession(false);
			String hexKeytoken=(String) httpSession.getAttribute("hexKeypassword");
			String ivHextoken=(String) httpSession.getAttribute("ivHexpassword");
			
			if(oldPassword!=null) {
				oldPassword=aesServiceForPasswordEncryption.getDecryptAESPassword(oldPassword, ivHextoken, hexKeytoken);
			}
			
			if(newPassword!=null) {
				newPassword=	aesServiceForPasswordEncryption.getDecryptAESPassword(newPassword, ivHextoken, hexKeytoken);
			}
			
			if(confirmPassword!=null) {
				confirmPassword=	aesServiceForPasswordEncryption.getDecryptAESPassword(confirmPassword, ivHextoken, hexKeytoken);
				}
	  
				if (!newPassword.isEmpty() || !newPassword.isBlank()) {
	  
					if(!newPassword.matches(regex)) {
					errors.put("new-password","* Password does not match the security criteria!"
					); } 
				  
				  
				  }
	         oldPassword= SHA256WithSaltNew.sha256WithSalt(oldPassword, CommonConstant.SHA_KEY_256);
	         newPassword= SHA256WithSaltNew.sha256WithSalt(newPassword, CommonConstant.SHA_KEY_256);
	         confirmPassword= SHA256WithSaltNew.sha256WithSalt(confirmPassword, CommonConstant.SHA_KEY_256);



	  Optional<Login>loginUserDetailsOptional = loginRepository.getByForcePersonalId(loginForcePersonalId); 
	  Login login= loginUserDetailsOptional.get();
	  password=login.getPassword();
	  password1=login.getPassword1(); 
	  password2=login.getPassword2();
	  
	  
	  if(!bCryptPasswordEncoder.matches(oldPassword, password)&&
	  !bCryptPasswordEncoder.matches(oldPassword, password1)&&
	  !bCryptPasswordEncoder.matches(oldPassword, password2)) {
	  errors.put("old-password", "* Old password doest match");
	  
	  }
	  
	  if (newPassword.isEmpty() || newPassword.isBlank() || newPassword == null) {
	  
	  errors.put("newPassword", "* Password already exists!"); }
	  
	  
	  
	  
	  if (loginForcePersonalId.isEmpty() || loginForcePersonalId.isBlank() || loginForcePersonalId == null) {
	  
	  errors.put("loginForcePersonalId", "* Force Personal Id is not available");
	  }
	  
	  if (oldPassword.isEmpty() || oldPassword.isBlank() || oldPassword == null) {
	  
	  errors.put("old-password", "* Old password Cannot be null"); }
	  
	  if (newPassword.isEmpty() || newPassword.isBlank() || newPassword == null)
	  {
	  
	  errors.put("new-password","* New password Cannot be null" ); }
	  
	  if (confirmPassword.isEmpty() || confirmPassword.isBlank() || confirmPassword
	  == null) {
	  
	  errors.put("confirm-password","* Confirm password Cannot be null" ); }
	  if((!confirmPassword.isEmpty() || !confirmPassword.isBlank() ||
	  confirmPassword != null)&& (!newPassword.isEmpty() ||!
	  newPassword.isBlank())){ if (!newPassword.equals(confirmPassword)) {
	  errors.put("confirm-password",
	  "* Confirm Password should match New Password"); } }
	  
	  
	 
	  
	  if (!newPassword.isEmpty() || !newPassword.isBlank()) {
	  
	  if(bCryptPasswordEncoder.matches(newPassword, password)|| bCryptPasswordEncoder.matches(newPassword, password1)||bCryptPasswordEncoder.matches(newPassword, password2)) {
	  errors.put("new-password", "* Password already exists!");
	  
	  } }
	  
	  
	  
	  if (!errors.isEmpty()) { response.put("isValid", false);
	  response.put("errors", errors); return ResponseEntity.ok(response);
	  
	  } else {
	  
	  if(loginUserDetails.updateByUserName(newPassword, password, password1,loginForcePersonalId)) {
      //httpResponse.sendRedirect("/initial-login");
	  response.put("isValid", true);
	  return ResponseEntity.ok(response);
	 
	  
	  }
	  
	  else{ response.put("isValid", false); response.put("errors", errors); return
	  ResponseEntity.ok(response); } }
	  
	  } catch (Exception e) { e.printStackTrace();
	  
	  }
	  
	  return null;
	  
	  
	  
	  }
	
	
	
	@RequestMapping(value = "change-password-encryption-key",method = RequestMethod.POST)
	public ResponseEntity<?> changePasswordEncryptionKey(Model model,HttpSession httpSession){
		
		 String secretKey=HexStringToByteArray.keyGeneraterHex(32);
	        String ivParam=HexStringToByteArray.keyGeneraterHex(16);
			String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
	        httpSession.setAttribute("hexKeypassword",secretKey);
	        httpSession.setAttribute("ivHexpassword",ivParam); 
	        Map<String,String> map =new HashMap<>();
	        map.put("hexKey", secretKey);
	        map.put("ivHex", ivParam);
	        map.put("loginForcePersonalId", loginForcePersonalId);
	        
	        return new ResponseEntity<>(HttpStatus.OK).ok(map);
	      
	}
	
	
	
	
}
