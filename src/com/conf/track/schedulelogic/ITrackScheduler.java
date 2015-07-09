package com.conf.track.schedulelogic;

import java.util.List;

import com.conf.exception.TrackScheduleException;
import com.conf.model.Talk;
import com.conf.model.Track;

public interface ITrackScheduler {
	
	/**
	* This method validates the input talk data for further processing 
	* @param talkList List of Talk Data taken from user input file
	* @return List<Talk>
	* @exception TrackScheduleException
	*/
	public List<Talk> validateAndCreateTalks(List<String> talkList) throws TrackScheduleException;
	
	
	/**
	* This method creates a scheduled track List. Each Track object would have morning and evening
	* Talk List. This method iterates through the List of Talk and generates morning and evening  Talks 
	* @param talkList List of validated Talk Data 
	* @return List<Track>
	* @exception TrackScheduleException
	*/
	public List<Track> getScheduledTracks(List<Talk> talkList) throws TrackScheduleException;

}
