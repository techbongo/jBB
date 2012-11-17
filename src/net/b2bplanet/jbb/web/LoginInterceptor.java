package net.b2bplanet.jbb.web;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor implements
StrutsStatics {

	private static final Log log = LogFactory.getLog(LoginInterceptor.class);
	private static final String USER_HANDLE = "loggedInUser";
	private static final String LOGIN_ATTEMPT = "loginAttempt";


	public void init() {
		log.info("Initializing jBB Application...");
	}

	public void destroy() {
		log.info("jBB Application Initialization Complete...");
	}

	public String intercept(ActionInvocation invocation) throws Exception {

		final ActionContext context = invocation.getInvocationContext();
		Map<String, Object> session = context.getSession();

		// Is there a "user" object stored in the user's HttpSession?
		Object user = session.get(USER_HANDLE);
		if (user == null) {
			// The user has not logged in yet.

			// Is the user attempting to log in right now?
			String loginAttempt = (String) context.getParameters().get(LOGIN_ATTEMPT);

			/* The user is attempting to log in. */
			if (!StringUtils.isBlank(loginAttempt)) {
				return invocation.invoke();
			}
			return "login";
		} else {
			return invocation.invoke();
		}
	}


}
