package nic.ame.app.security.config;


import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.session.CustomLogoutSuccessHandler;
import nic.ame.app.security.service.CustomAuthenticationProvider;
import nic.ame.constant.CommonConstant;

import java.util.Arrays;



import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("unused")
	private final CustomAuthenticationProvider customAuthenticationProvider;
    @SuppressWarnings("unused")
	private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final FilterMethod filterMethod;
    private final JdbcTemplate jdbcTemplate;

    

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider,
			CustomAuthenticationFailureHandler customAuthenticationFailureHandler, FilterMethod filterMethod,JdbcTemplate jdbcTemplate) {
		super();
		this.customAuthenticationProvider = customAuthenticationProvider;
		this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
		this.filterMethod = filterMethod;
		this.jdbcTemplate=jdbcTemplate;
	}

	@Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HTTP Strict Transport Security (HSTS) configuration
        http.headers(headers -> headers
                .httpStrictTransportSecurity()
                .requestMatcher(AnyRequestMatcher.INSTANCE)
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000));

        // CSRF configuration
       /* http.csrf()
            .requireCsrfProtectionMatcher(new AllExceptUrlsStartedWith("/eSign/response", "/application-under-process-ma"))
            .csrfTokenRepository(csrfTokenRepository()); // Ensure CSRF Token Repository is set
*/
        http.csrf(csrf -> csrf.ignoringRequestMatchers(
                "/final-response/**","/decleration-response/**",
                "/application-under-process-ma/**", "/response-digial-sign/**","/esign-response-close/**","/viewAmeFinalPdf/**","/viewPdf/**",
                "/esign-decleration-pdf/**","/captcha/**","/refresh-captcha","/ViewDocuments/**","/view-ame-final-pdf/**",
                "/declaration-view/**","/view-final-report-ame/**","/view-declaration-pdf-ame/**","upload-form","user-manual"
        )).cors(withDefaults());
        
        http .headers(headers -> headers
                .frameOptions().sameOrigin().contentSecurityPolicy("frame-ancestors 'self' https://ame.capf.gov.in"));
        // Authorization rules
        http.addFilterBefore(filterMethod,UsernamePasswordAuthenticationFilter.class).
        authorizeHttpRequests(authorizeRequests -> authorizeRequests
        		.requestMatchers("/view-final-report-ame/**","/file/**","/view-declaration-pdf-ame/**","upload-form","user-manual").permitAll()

        		.requestMatchers("/ViewDocuments/**","/view-ame-final-pdf/**","/declaration-view/**").permitAll()
            .requestMatchers("/final-response/**","/decleration-response/**","/refresh-captcha","/esign-response-close/**","/login-process").permitAll()
            .requestMatchers("/response-digial-sign/**","/viewAmeFinalPdf/**","/viewPdf/**","/generatePdf/**","/esign-decleration-pdf/**").permitAll()
            .requestMatchers("/application-under-process-ma/**","/esign-response-close/**").permitAll()
            .requestMatchers("/vendor/**", "/js/**", "/css/**", "/css/local/master1.css", "/ame-css/**",
                             "/ame-js/**", "/fonts/**", "/images/**", "/save-login", "/admin", "/datatable-css/**",
                             "/datatable-js/**", "/index/**", "/inner", "/contactus", "/published-notifications",
                             "/download-notification-file", "/form-index-page-ama", "/captcha/**", "/user-ame-result",
                             "/favicon.ico", "/error","/download-ame-final-report-uploaded","/delete-final-comment-for-down-category/**","/ame-assessment-final-details/**").permitAll()
            .anyRequest().authenticated());

        // Custom login and logout configuration
        http.formLogin(formLogin -> formLogin
            .loginPage("/initial-login")
            .loginProcessingUrl("/login-process") 
            .successForwardUrl("/ame")
            .failureUrl("/initial-login")
            .permitAll())
            .logout(logout -> logout
                .logoutUrl("/sign-out")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler(jdbcTemplate))
                .logoutSuccessUrl("/initial-login"));

        // Session management
		
		  http.sessionManagement(sessionManagement -> sessionManagement
		 .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		  .sessionFixation().migrateSession() .invalidSessionUrl("/initial-login")
		  .maximumSessions(1).maxSessionsPreventsLogin(false)
		  .expiredUrl("/initial-login"));
		 

	
        // CORS configuration
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // Security headers
        http.headers(headers -> headers
                .referrerPolicy(referrerPolicy -> referrerPolicy.policy(
                        org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN))
                .xssProtection(xssProtection -> xssProtection.disable()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Allow all origins
        
    
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow only GET and POST methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;  
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
     CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        repository.setCookieHttpOnly(false); // Allow JavaScript access to the cookie
        repository.setCookieName("XSRF-TOKEN");
        repository.setCookiePath("/");
        repository.setSecure(true);
        return repository;
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    private static class AllExceptUrlsStartedWith implements RequestMatcher {
        private static final String[] ALLOWED_METHODS =
            new String[] {"GET", "HEAD", "TRACE", "OPTIONS"};

        private final String[] allowedUrls;

        public AllExceptUrlsStartedWith(String... allowedUrls) {
            this.allowedUrls = allowedUrls;
        }
 
        @Override
        public boolean matches(HttpServletRequest request) {
            String method = request.getMethod();
            for (String allowedMethod : ALLOWED_METHODS) {
                if (allowedMethod.equals(method)) {
                    return false; // Allow specified methods
                }
            }

            String uri = request.getRequestURI();
            for (String allowedUrl : allowedUrls) {
                if (uri.startsWith(allowedUrl)) {
                    return false; // Allow specified URL patterns
                }
            }

            return true; // Block all other requests
        }
    }
}
