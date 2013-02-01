package core.utils;

import javax.servlet.http.HttpServletRequest;

import core.db.UserContext;

public class ServletUtils {

	private static final String USER_CONTEXT = "userContext";
	
	public static void setUserContext(HttpServletRequest request, UserContext userContext) {
		request.getSession().setAttribute(USER_CONTEXT, userContext);
	}
	
	public static UserContext getUserContext(HttpServletRequest request) {
		UserContext userContext = (UserContext) request.getSession().getAttribute(USER_CONTEXT);
		if (userContext == null) {
			userContext = new UserContext();
			userContext.getUsuario().setId(1);
			setUserContext(request, userContext);
		}
		return userContext;
	}
	
}
