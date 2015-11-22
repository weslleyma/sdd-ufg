package br.ufg.inf.sdd_ufg.resource.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionTokenResponse {

	@JsonProperty("session_token")
	private String sessionToken;
	
	public SessionTokenResponse() {
		
	}
	
	public SessionTokenResponse(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	
	
}
