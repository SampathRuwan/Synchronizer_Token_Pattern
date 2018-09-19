package com.example.demo.model;

public class CookieModel {

	private String sessionId;
    private String csrfToken;
    private String Id;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	
}
