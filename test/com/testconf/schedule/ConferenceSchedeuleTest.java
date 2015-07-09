package com.testconf.schedule;


import com.conf.exception.TrackScheduleException;
import com.conf.schedule.IConferenceScheduler;
import com.conf.scheduleImpl.ConferenceSchedulerImpl;

import  org.junit.Test;

public class ConferenceSchedeuleTest {

		/**
		* This method tests the ConferenceScheduleImpl.scheduleConference method 
		* for valid case provided with valid txt file
		* 		
		*/
	 	@Test
	    public void scheduleConferenceTest() throws TrackScheduleException {
		 
		 IConferenceScheduler confScheduler = new ConferenceSchedulerImpl();
			
		 confScheduler.scheduleConference("ConferenceData.txt");
		 
	    }
	 	
	 	
	 	/**
		* This method tests the ConferenceScheduleImpl.scheduleConference method 
		* for invalid case provided with valid txt file
		* 		
		*/
	 	@Test(expected = TrackScheduleException.class)
	    public void scheduleConferenceInvalidTest() throws TrackScheduleException {
		 
		 IConferenceScheduler confScheduler = new ConferenceSchedulerImpl();
			
		 confScheduler.scheduleConference("ConferenceData_Invalid.txt");
		 
	    }
	 	
	 	
}
