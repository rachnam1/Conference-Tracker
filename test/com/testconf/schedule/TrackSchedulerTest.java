package com.testconf.schedule;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import com.conf.exception.TrackScheduleException;
import com.conf.model.Talk;
import com.conf.model.Track;
import com.conf.track.schedulelogic.ITrackScheduler;
import com.conf.track.schedulelogic.TrackSchedulerImpl;
public class TrackSchedulerTest {
	
	
	/**
	* This method tests the ITrackScheduler.validateAndCreateTalks method 
	* for valid case 
	* 		
	*/
	@Test
	public void validateAndCreateTalksTest() throws TrackScheduleException{
		
		List<String> talkDetailsList = createTalkDetailsList();
		ITrackScheduler trackScheuler = new TrackSchedulerImpl();
		List<Talk> talkList =	trackScheuler.validateAndCreateTalks(talkDetailsList);
		assertEquals(talkList.size(), talkDetailsList.size() );
	}
	
	
	/**
	* This method tests the ITrackScheduler.validateAndCreateTalks method 
	* for valid case 
	* 		
	*/
	@Test(expected = TrackScheduleException.class)
	public void validateAndCreateTalksInvalidTest() throws TrackScheduleException{
		
		List<String> talkDetailsList = createTalkDetailsList();
		String talkDetail = talkDetailsList.get(0);
		String newTalk = talkDetail.substring(0, talkDetail.length() - 2);
		
		talkDetailsList.add(newTalk);
		ITrackScheduler trackScheuler = new TrackSchedulerImpl();
		List<Talk> talkList =	trackScheuler.validateAndCreateTalks(talkDetailsList);
		assertEquals(talkList.size(), talkDetailsList.size() );
	}
	

	/**
	* This method tests the ITrackScheduler.validateAndCreateTalks method 
	* for valid case 
	* 		
	*/
	@Test
	public void getScheduledTracksTest() throws TrackScheduleException{
		
		List<String> talkDetailsList = createTalkDetailsList();
		ITrackScheduler trackScheuler = new TrackSchedulerImpl();
		List<Talk> talkList =	trackScheuler.validateAndCreateTalks(talkDetailsList);
		
		List<Track> trackObjects = trackScheuler.getScheduledTracks(talkList);
		Track track = trackObjects.get(0);
		String scheduledTime = track.getSession().getMorningTalk().get(0).getScheduledTime();
		
		assertEquals(scheduledTime.trim(),"09:00AM");
	}


	/**
	* This method creates the test data for List<String> of Talks 
	* 		
	*/
	private List<String> createTalkDetailsList(){
		List<String> trackDetailsList = new ArrayList<String>();
		trackDetailsList.add("Writing Fast Tests Against Enterprise Rails 60min");
		trackDetailsList.add("Overdoing it in Python 45min");
		trackDetailsList.add("Lua for the Masses 30min");
		trackDetailsList.add("Ruby Errors from Mismatched Gem Versions 45min");
		trackDetailsList.add("Common Ruby Errors 45min");
		trackDetailsList.add("Rails for Python Developers lightning");
		trackDetailsList.add("Communicating Over Distance 60min");
		trackDetailsList.add("Accounting-Driven Development 45min");
		trackDetailsList.add("Woah 30min");
		trackDetailsList.add("Sit Down and Write 30min");
		trackDetailsList.add("Pair Programming vs Noise 45min");
		trackDetailsList.add("Rails Magic 60min");
		trackDetailsList.add("Ruby on Rails: Why We Should Move On 60min");
		trackDetailsList.add("Clojure Ate Scala (on my project) 45min");
		trackDetailsList.add("Programming in the Boondocks of Seattle 30min");
		trackDetailsList.add("Ruby vs. Clojure for Back-End Development 30min");
		trackDetailsList.add("Programming in the Boondocks of Seattle 30min");
		trackDetailsList.add("Ruby on Rails Legacy App Maintenance 60min");
		trackDetailsList.add("A World Without HackerNews 30min");
		trackDetailsList.add("User Interface CSS in Rails Apps 30min");
		
		return trackDetailsList;
	}
}
