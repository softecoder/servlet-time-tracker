package session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionLogic {
    public static boolean sessionIsDead = true;

    public static HttpSession getSession(HttpServletRequest request) {
        HttpSession session ;
        if (sessionIsDead) {
            session = request.getSession();
            sessionIsDead = false;
        } else {
            session = request.getSession(false);
        }
        return session;
    }

    public static boolean isSessionNotAlive(HttpSession session) {
        return session == null;
    }
}