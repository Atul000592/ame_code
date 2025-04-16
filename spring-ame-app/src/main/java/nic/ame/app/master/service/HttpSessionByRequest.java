package nic.ame.app.master.service;

/*
 * import jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpSession; import org.apache.catalina.Manager; import
 * org.apache.catalina.Session; import org.apache.catalina.core.StandardSession;
 * import org.apache.catalina.core.StandardManager;
 * 
 * // Method to get the session by JSESSIONID public HttpSession
 * getSessionById(HttpServletRequest request, String sessionId) { // Get the
 * current session, but don't create a new one HttpSession currentSession =
 * request.getSession(false);
 * 
 * if (currentSession == null) {
 * System.out.println("No current session found."); return null; // No session
 * exists }
 * 
 * // Get the session manager Manager manager = ((StandardSession)
 * currentSession).getManager();
 * 
 * // Retrieve the session using the session ID Session session =
 * manager.findSession(sessionId);
 * 
 * if (session != null) { System.out.println("Session found with ID: " +
 * sessionId); return (HttpSession) session; // Cast to HttpSession if necessary
 * } else { System.out.println("Session not found for ID: " + sessionId); return
 * null; // Session not found } }
 */
