package nic.ame.app.master.session;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final JdbcTemplate jdbcTemplate;

    public CustomLogoutSuccessHandler(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Get session ID and delete from DB
        String sessionId = request.getRequestedSessionId();
        if (sessionId != null) {
            jdbcTemplate.update("DELETE FROM SPRING_SESSION WHERE SESSION_ID = ?", sessionId);
        }
        response.sendRedirect("/login?logout");
    }
}

