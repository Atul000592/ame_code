package nic.ame.app.master.session;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SessionManagement {
	
	 @Autowired
	 private JdbcIndexedSessionRepository sessionRepository;
	
	public Session getSessionById(String sessionId)
	{
		
		 Session session = sessionRepository.findById(sessionId);
        
		 if (session != null) {
	            return session;
	        } else {
	            return null;
	        }
    }

}
