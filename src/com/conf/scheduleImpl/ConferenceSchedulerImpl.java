
/**
* The ConferenceSchedulerImpl implements an IConferenceScheduler that
* provides and interface to user to schedule the Conference details given the 
* Talk Input in txt file.
*
*/
package com.conf.scheduleImpl;
import java.util.List;

import com.conf.exception.TrackScheduleException;
import com.conf.model.Session;
import com.conf.model.Talk;
import com.conf.model.Track;
import com.conf.schedule.IConferenceScheduler;
import com.conf.track.schedulelogic.ITrackScheduler;
import com.conf.track.schedulelogic.TrackSchedulerImpl;
import com.conf.util.FileReader;

public class ConferenceSchedulerImpl implements IConferenceScheduler{
	
	private FileReader fr;
	
	private ITrackScheduler trackScheduler;
	
	public ConferenceSchedulerImpl(){
		fr = new FileReader();
		trackScheduler  = new TrackSchedulerImpl();
	}
	
	/**
	* This method schedules the conference 
	* @param inputFileName name of the file containing the input data
	* @return Nothing
	* @exception Exception
	*/
	public void scheduleConference(String inputFileName) throws TrackScheduleException{
		
		List<String> trackInput = fr.readInput(inputFileName);
		List<Talk> validatedTalkList = trackScheduler.validateAndCreateTalks(trackInput);
		List<Track> talkList = trackScheduler.getScheduledTracks(validatedTalkList);
		
		printConferenceDetails(talkList);
	}
	
	
	/**
	* This method prints the scheduled conference Details
	* @param trackList list of Track Objects that is been scheduled for the conference.
	* @return Nothing
	* 
	*/
	private void printConferenceDetails(List<Track> trackList){
		
		
		for (Track track : trackList) {
			System.out.println(track.getTrackId());
			Session session = track.getSession();
			List<Talk> morningTalkList = session.getMorningTalk();
			for(Talk talk : morningTalkList){
				System.out.println(talk.getScheduledTime() + " " + talk.getTalkTitle());
			}
			
			Talk lunchBreak = session.getLunchBreak();
			System.out.println(lunchBreak.getScheduledTime() + " " + lunchBreak.getTalkTitle());
			
			List<Talk> eveningTalkList = session.getEveningTalk();
			for(Talk talk : eveningTalkList){
				System.out.println(talk.getScheduledTime() + " " + talk.getTalkTitle());
			}
			
			Talk networkSession = session.getNetworkSession();
			System.out.println(networkSession.getScheduledTime() + " " + networkSession.getTalkTitle());
		}
	}

}
