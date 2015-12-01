package br.ufg.inf.sdd_ufg.resource.utils;

public class ErrorResponse {

	private Integer status;
	private String message;
	
	public ErrorResponse() {
		
	}

	public ErrorResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
