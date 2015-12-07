package br.ufg.inf.sdd_ufg;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import br.ufg.inf.sdd_ufg.dao.UserDao;
import br.ufg.inf.sdd_ufg.model.User;
import br.ufg.inf.sdd_ufg.model.enums.HttpHeaders;
import br.ufg.inf.sdd_ufg.resource.utils.ErrorResponse;

import com.yammer.dropwizard.testing.JsonHelpers;

public class AuthenticationRequestFilter implements Filter {
	
	private static final Integer SESSION_MAX_DURATION = 30; // Min
	
	FilterConfig config;
	private final UserDao userDao;
	
	@Inject
	public AuthenticationRequestFilter(UserDao userDao) {
		this.userDao = userDao;
	}
	

	public void setFilterConfig(FilterConfig config) {
		this.config = config;
	}

	public FilterConfig getFilterConfig() {
		return config;
	}

	public void init(FilterConfig config) throws ServletException {
		setFilterConfig(config);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        		
		String sessionToken = httpRequest.getHeader(HttpHeaders.SESSION_TOKEN.toString());
		
		User user = userDao.findUserByToken(sessionToken);
		
		if (user != null) {
			Long sessionDurationMs = new Date().getTime() - user.getTokenCreatedAt().getTime();
			Long sessionDurationM = TimeUnit.MILLISECONDS.toMinutes(sessionDurationMs);
			if (sessionDurationM.intValue() < SESSION_MAX_DURATION) {
				httpRequest.setAttribute("Logged-User", user);
				chain.doFilter(httpRequest, response);
				return;
			}
		}
		if (!httpRequest.getMethod().equals("POST") && !httpRequest.getContextPath().contains("users")) {
			buildErrorResponse(response);
			return;
		}
		chain.doFilter(httpRequest, response);
	}
	
	private void buildErrorResponse(ServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpResponse.setContentType("application/json");
		httpResponse.setStatus(Response.Status.FORBIDDEN.getStatusCode());
		
		ErrorResponse responseError = new ErrorResponse(Response.Status.FORBIDDEN.getStatusCode(),
				"You have no permission to access this resource");
		
		
		httpResponse.getWriter().print(JsonHelpers.asJson(responseError));
		httpResponse.getWriter().close();
	}

	public void destroy() {}

}