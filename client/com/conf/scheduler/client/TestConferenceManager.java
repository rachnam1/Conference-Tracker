package com.conf.scheduler.client;

import com.conf.exception.TrackScheduleException;
import com.conf.schedule.IConferenceScheduler;
import com.conf.scheduleImpl.ConferenceSchedulerImpl;

public class TestConferenceManager {

	public static void main(String[] args) {
	
		IConferenceScheduler confScheduler = new ConferenceSchedulerImpl();
		try {
			confScheduler.scheduleConference("ConferenceData.txt");
		} catch (TrackScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
