package br.ufg.inf.sdd_ufg.model.enums;

public enum HttpHeaders {
	LOCATION("Location"), SESSION_TOKEN("Session-Token");

    private final String name;       

    private HttpHeaders(String value) {
        name = value;
    }
    
    public boolean equals(String otherName) {
    	return equalsName(otherName);
    }
    
    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}
