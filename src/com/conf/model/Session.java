/**
* model object for each session containing list of morning and 
* evening talks. Lunch Break and Network session
*
*/

package com.conf.model;
import java.util.List;

public class Session {
	
	// List of Morning Talk
	private List<Talk> morningTalk;
	
	// List of Evening Talk
	private List<Talk> eveningTalk;
	
	// Lunch Break 
	private Talk lunchBreak;
	
	// Network session
	private Talk networkSession;

	public List<Talk> getMorningTalk() {
		return morningTalk;
	}

	public void setMorningTalk(List<Talk> morningTalk) {
		this.morningTalk = morningTalk;
	}

	public List<Talk> getEveningTalk() {
		return eveningTalk;
	}

	public void setEveningTalk(List<Talk> eveningTalk) {
		this.eveningTalk = eveningTalk;
	}

	public Talk getLunchBreak() {
		return lunchBreak;
	}

	public void setLunchBreak(Talk lunchBreak) {
		this.lunchBreak = lunchBreak;
	}

	public Talk getNetworkSession() {
		return networkSession;
	}

	public void setNetworkSession(Talk networkSession) {
		this.networkSession = networkSession;
	}
	
	

}
