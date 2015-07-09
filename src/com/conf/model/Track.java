
/**
* model object for having Track details.
*
*/
package com.conf.model;
public class Track {

	// Id for Track
	private String trackId;
	
	// Talk Session object
	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}
	
	
}

