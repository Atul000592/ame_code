package nic.ame.app.master.session;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SessionCleanupTask {

	Logger  logger=LoggerFactory.getLogger(SessionCleanupTask.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
   // @Scheduled(cron = "0/120 * * * * *")// Run 2 minites (or adjust as needed)
    @Scheduled(cron = "0 0/30 * * * *") // Run every 10 minites  (or adjust as needed)
    public void cleanExpiredSessions() {
        String sql = "DELETE FROM SPRING_SESSION WHERE EXPIRY_TIME < ? OR PRINCIPAL_NAME is null";
        jdbcTemplate.update(sql, System.currentTimeMillis());
        logger.info("Seesion Exipired And clean by Schedular"+Calendar.getInstance().getTime());
    }
}

