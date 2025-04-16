package nic.ame.app.security.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.exceptionHandler.LoginAttemptFailedException;
import nic.ame.app.master.model.Login;
import nic.ame.app.master.model.SHARandomKeyHolder;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.repository.SHARandomKeyHolderRepository;
import nic.ame.app.master.service.DecryptAESServiceForPasswordEncryption;
import nic.ame.app.master.service.LoginLogoutService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CompareDateUtil;
import nic.ame.master.util.LoginAppendedSeparator;
import nic.ame.master.util.SHA256WithSalt;
import nic.ame.master.util.SeparateShaAndKey;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8160193443860135084L;

	
	Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	private static final int MAX_FAILED_ATTEMPTS = 5;

	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final HttpRequestUtil httpRequestUtil;
	private final DecryptAESServiceForPasswordEncryption aesServiceForPasswordEncryption;
	private final LoginRepository loginRepository;
	private final LoginLogoutService loginLogoutService;
	private final SHARandomKeyHolderRepository randomKeyHolderRepository;

	@Autowired
	public CustomAuthenticationProvider(UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder, HttpRequestUtil httpRequestUtil,
			DecryptAESServiceForPasswordEncryption aesServiceForPasswordEncryption, LoginRepository loginRepository,
			LoginLogoutService loginLogoutService, SHARandomKeyHolderRepository randomKeyHolderRepository) {
		super();
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.httpRequestUtil = httpRequestUtil;
		this.aesServiceForPasswordEncryption = aesServiceForPasswordEncryption;
		this.loginRepository = loginRepository;
		this.loginLogoutService = loginLogoutService;
		this.randomKeyHolderRepository = randomKeyHolderRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		logger.info("in custom Auth Class...........");
		HttpSession httpSession = httpRequestUtil.getCurrentHttpRequest().getSession();
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		//String randomKey=(String)httpSession.getAttribute("randomKey");
		
		if(password==null) {
			httpSession.setAttribute("errorMsg", "invalid username or password ......login failed....");
			throw new LoginAttemptFailedException("invalid username or password ......login failed....");
		}else {
			
			
			String id=LoginAppendedSeparator.getLastTenLetters(password,CommonConstant.GET_ID_CODE);
			
			String randomKeyHASH=LoginAppendedSeparator.getLastTenLetters(password,CommonConstant.GET_HASH_PASSWORD);
			randomKeyHASH=SeparateShaAndKey.removeLastCharacters(randomKeyHASH,CommonConstant.GET_ID_CODE);
		     
			
			Optional<SHARandomKeyHolder>optional=randomKeyHolderRepository.findById(Integer.parseInt(id));
			if(optional.isEmpty()) {
				httpSession.setAttribute("errorMsg", "invalid username or password ......login failed....");
				throw new LoginAttemptFailedException("invalid username or password ......login failed....");
			}else {
				if(!optional.get().getRandomKey().equals(randomKeyHASH)) 
				 {
					randomKeyHolderRepository.deleteById(Integer.parseInt(id));
					httpSession.setAttribute("errorMsg", "invalid username or password ......login failed....");
					throw new LoginAttemptFailedException("invalid username or password ......login failed....");
				 }
				randomKeyHolderRepository.deleteById(Integer.parseInt(id));

				
			}
			
			
				password=SeparateShaAndKey.removeLastCharacters(password,CommonConstant.GET_HASH_PASSWORD);
			}
		
		
		     httpSession.removeAttribute("randomKey");
		
		if (loginRepository.findByUserName(username).isEmpty()) {

			httpSession.setAttribute("errorMsg", "invalid username or password ......login failed....");
			throw new LoginAttemptFailedException("invalid username or password ......login failed....");
		}
          
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
 
		if (userDetails != null && bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {

			Login loginForLock = new Login();
			loginForLock = loginRepository.findByUserName(userDetails.getUsername()).get();

			if (loginForLock.getUserStatus() != 1) {

				logger.info("User Account is locked >>>>>");
				httpSession.setAttribute("errorMsg",
						"invalid username or password ......account locked for 60 mins...");

				int lockResult = CompareDateUtil.validateLockDateAndTime(loginForLock.getLockReleaseTime());

				logger.info("lock Status : " + lockResult);
				if (lockResult == 0) {
					logger.info("User Account is Unlocked >>>>> after 60 min of lock time");
					resetFailedAttempts(userDetails.getUsername());
					return new UsernamePasswordAuthenticationToken(username, password,
							new ArrayList<GrantedAuthority>());
				}
				throw new LoginAttemptFailedException("Invalid username or password");
			}
			resetFailedAttempts(userDetails.getUsername());
			logger.info("User Account is Unlocked >>>>>");
			return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
		} else {

			handleFailedAttempt(username);
			httpSession.setAttribute("errorMsg", "invalid username or password ......login failed....");
			throw new LoginAttemptFailedException("invalid username or password ......login failed....");
			// throw new javax.security.sasl.AuthenticationException()

		}

	}



	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private void handleFailedAttempt(String userName) {
		Optional<Login> user = loginRepository.findByUserName(userName);
		if (!user.isEmpty()) {
			Login login = new Login();
			login = user.get();
			int attempts = login.getFailedLoginAttempts() + 1;
			login.setFailedLoginAttempts(attempts);
			if (attempts > MAX_FAILED_ATTEMPTS) {
				login.setUserStatus(CommonConstant.USER_ACCOUNT_LOCK);
				LocalDateTime currentTime = LocalDateTime.now();
				LocalDateTime updatedTime = currentTime.plusMinutes(60);
				Date currentDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
				Date updatedDate = Date.from(updatedTime.atZone(ZoneId.systemDefault()).toInstant());
				logger.info("login time >>>" + currentDate + "  lock relaese date: " + updatedDate);
				login.setLockReleaseTime(updatedDate);
				login.setStatusReason("exceed login limit::" + currentDate);
				loginRepository.save(login);
				logger.info("failed attempt status updated.......");
			} else {
				loginRepository.save(login);
				logger.info("failed attempt count increased.......");

			}
		}
	}

	private void resetFailedAttempts(String userName) {
		Optional<Login> user = loginRepository.findByUserName(userName);
		if (!user.isEmpty()) {
			Login login = new Login();
			login = user.get();
			login.setFailedLoginAttempts(0);
			login.setUserStatus(CommonConstant.USER_ACCOUNT_UNLOCK);
			login.setStatusReason("account unlock after correct password " + Calendar.getInstance().getTime());
			loginRepository.save(login);
		}
	}

}
