/**
* model object for having Talk details.
*
*/

package com.conf.model;
public class Talk {
	
	// Entire talk String including the duration
	private String talkTopic;
	
	// Talk Title
	public String talkTitle;
	
	// Duration for each Talk
	private int timeDuration;
	
	//boolean for Talk scheduled 
	private boolean scheduled = false;
	
	// Time when the talk is been scheduled.
    private String scheduledTime;
  
    public Talk(String talkTitle,String name, int time) {
        this.talkTitle = talkTitle;
        this.talkTopic = name;
        this.timeDuration = time;
    }
    
	public String getTalkTopic() {
		return talkTopic;
	}
	public void setTalkTopic(String talkTopic) {
		this.talkTopic = talkTopic;
	}
	public int getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(int timeDuration) {
		this.timeDuration = timeDuration;
	}
	public boolean isScheduled() {
		return scheduled;
	}
	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
	public String getScheduledTime() {
		return scheduledTime;
	}
	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getTalkTitle() {
		return talkTitle;
	}

	public void setTalkTitle(String talkTitle) {
		this.talkTitle = talkTitle;
	}

	
	

}
