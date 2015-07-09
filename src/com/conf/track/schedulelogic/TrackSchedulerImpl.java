/**
* Helper class that validates the input txt file and schedules the track
* with morning and evening session talks.
*
*/

package com.conf.track.schedulelogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.conf.constants.TrackConstants;
import com.conf.exception.TrackScheduleException;
import com.conf.model.Session;
import com.conf.model.Talk;
import com.conf.model.Track;

public class TrackSchedulerImpl implements ITrackScheduler{

	/**
	* This method validates the input talk data for further processing 
	* @param talkList List of Talk Data taken from user input file
	* @return List<Talk>
	* @exception TrackScheduleException
	*/
	public List<Talk> validateAndCreateTalks(List<String> talkList) throws TrackScheduleException
    {
        // If talksList is null throw exception invaid list to schedule.
        if(talkList == null)
            throw new TrackScheduleException("Empty Talk List");
        
        List<Talk> validTalksList = new ArrayList<Talk>();
        String errorMsg;
       
        // Iterate list and validate time.
        for(String talk : talkList)
        {
            int lastSpaceIndex = talk.lastIndexOf(" ");
            
            // if talk does not have any space, means either title or time is missing.
            if(lastSpaceIndex == -1){
            	errorMsg = "Invalid talk, Talk Tiltle : " + talk + ". Talk time must be specify.";
            	throw new TrackScheduleException(errorMsg,"INVALID_TALK_TITLE_EXCEPTION");
            }
                            
            String name = talk.substring(0, lastSpaceIndex);
            String timeStr = talk.substring(lastSpaceIndex + 1);
            
            // If title is missing or blank.
            if(name == null || "".equals(name.trim())){
            	errorMsg = "Invalid talk name, " + talk;
            	throw new TrackScheduleException(errorMsg,"INVALID_TALK_TITLE_EXCEPTION");
            }
            
            // If time is not ended with min or lightning.
            else if(!timeStr.endsWith(TrackConstants.MIN_SUFFIX) && !timeStr.endsWith(TrackConstants.LIGHTNING_SUFFIX)){
            	errorMsg = "Invalid talk time, Talk Tiltle : " + talk + ". Time must be in min or in lightning";
            	throw new TrackScheduleException(errorMsg,"INVALID_TALK_TITLE_EXCEPTION");
            }
            
           
            int time = 0;
            
            // Parse time from the time string .
            try{
                if(timeStr.endsWith(TrackConstants.MIN_SUFFIX)) {
                    time = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(TrackConstants.MIN_SUFFIX)));
                }
                else if(timeStr.endsWith(TrackConstants.LIGHTNING_SUFFIX)) {
                    String lightningTime = timeStr.substring(0, timeStr.indexOf(TrackConstants.LIGHTNING_SUFFIX));
                    if("".equals(lightningTime))
                        time = 5;
                    else
                        time = Integer.parseInt(lightningTime) * 5;
                }
            }catch(NumberFormatException nfe) {
            	errorMsg = "Unbale to parse time " + timeStr + " for talk " + talk;
            	throw new TrackScheduleException(errorMsg,"INVALID_TALK_TITLE_EXCEPTION");
            }
            
            // Add talk to the valid talk List.
            validTalksList.add(new Talk(talk,name, time));
        }
        
        return validTalksList;
    }
	

	/**
	* This method creates a scheduled track List. Each Track object would have morning and evening
	* Talk List. This method iterates through the List of Talk and generates morning and evening  Talks 
	* @param talkList List of validated Talk Data 
	* @return List<Track>
	* @exception TrackScheduleException
	*/
	public List<Track> getScheduledTracks(List<Talk> talkList) throws TrackScheduleException{
		
		//Find the total possible tracks.
        int perDayMinTime = TrackConstants.CONF_HOURS_PERDAY * 60;
        int totalTalksTime = getTotalTalksTime(talkList);
        int totalPossibleTracks = totalTalksTime/perDayMinTime;
        String errorMsg = null;
        
        List<Talk> talksListForOperation = new ArrayList<Talk>(); 
        talksListForOperation.addAll(talkList);
        
        List<List<Talk>> combForMornTalks = findPossibleCombSession(talksListForOperation, totalPossibleTracks, true);
        
        // Remove all the scheduled talks for morning Talk, from the operationList.
        for(List<Talk> talkLst : combForMornTalks) {
            talksListForOperation.removeAll(talkLst);
        }
        
        // Find possible combinations for the evening Talk.
        List<List<Talk>> combForEveTalk = findPossibleCombSession(talksListForOperation, totalPossibleTracks, false);
        
        // Remove all the scheduled talks for evening session, from the operationList.
        for(List<Talk> talkLst : combForEveTalk) {
            talksListForOperation.removeAll(talkLst);
        }
        	
        	
        	
     // check if the operation list is not empty, then try to fill all the remaining talks in evening session. 
        if(!talksListForOperation.isEmpty()) {
            List<Talk> scheduledTalkList = new ArrayList<Talk>();
            for(List<Talk> talkLst : combForEveTalk) {
                int totalTime = getTotalTalksTime(talkLst);
                
                for(Talk talk : talksListForOperation) {
                    int talkTime = talk.getTimeDuration();
                    
                    if(talkTime + totalTime <= TrackConstants.MAX_SESSION_TIMELIMIT) {
                        talkList.add(talk);
                        talk.setScheduled(true);
                        scheduledTalkList.add(talk);
                    }
                }
                
                talksListForOperation.removeAll(scheduledTalkList);
                if(talksListForOperation.isEmpty())
                    break;
            }
        }
        
     // If operation list is still not empty, its mean the conference can not be scheduled with the provided data.
        if(!talksListForOperation.isEmpty())
        {
        	errorMsg = "Unable to schedule all task for conferencing.";
        	throw new TrackScheduleException(errorMsg,"INVALID_TALK_TITLE_EXCEPTION");
        }
        
        // Schedule the day event from morning session and evening session.
        return getScheduledTalksList(combForMornTalks, combForEveTalk);
		
	}
     
	
	
	 
	/**
	* This method loops through possible tracks and Creates a list of morning or evening 
	* Talks (morningSession input value true for morning session )
	* @param talksListForOperation    List of validated Talk Data 
	* @param totalPossibleTracks      total possible tracks
	* @param morningSession           boolean true if List of Morning Talk is to be computed.
	* @return int sum of  Talk duration
	* 
	*/
	 private List<List<Talk>> findPossibleCombSession(List<Talk> talksListForOperation, int totalPossibleTracks, boolean morningSession)
	    {
	        int minSessionTimeLimit = TrackConstants.MINIMUM_SESSION_LIMIY;
	        int maxSessionTimeLimit = TrackConstants.MAX_SESSION_TIMELIMIT;
	        
	        if(morningSession)
	            maxSessionTimeLimit = minSessionTimeLimit;
	        
	        int talkListSize = talksListForOperation.size();
	        List<List<Talk>> possibleCombinationsOfTalks = new ArrayList<List<Talk>>();
	        int possibleCombinationCount = 0;
	        
	        // Loop to get combination for total possible tracks.
	        // Check one by one from each talk to get possible combination.
	        for(int count = 0; count < talkListSize; count++) {
	            int startPoint = count;
	            int totalTime = 0;
	            List<Talk> possibleCombinationList = new ArrayList<Talk>();
	            
	            // Loop to get possible combination.
	            while(startPoint != talkListSize) {
	                int currentCount = startPoint;
	                startPoint++;
	                Talk currentTalk = talksListForOperation.get(currentCount);
	                if(currentTalk.isScheduled())
	                    continue;
	                int talkTime = currentTalk.getTimeDuration();
	                // If the current talk time is greater than maxSessionTimeLimit or 
	                // sum of the current time and total of talk time added in list  is greater than maxSessionTimeLimit.
	                // then continue.
	                if(talkTime > maxSessionTimeLimit || talkTime + totalTime > maxSessionTimeLimit) {
	                    continue;
	                }
	                
	                possibleCombinationList.add(currentTalk);
	                totalTime += talkTime;
	                
	                // If total time is completed for this session than break this loop.
	                if(morningSession) {
	                    if(totalTime == maxSessionTimeLimit)
	                        break;
	                }else if(totalTime >= minSessionTimeLimit)
	                    break;
	            }
	            
	            // Valid session time for morning session is equal to maxSessionTimeLimit.
	            // Valid session time for evening session is less than or eqaul to maxSessionTimeLimit and greater than or equal to minSessionTimeLimit.
	            boolean validSession = false;
	            if(morningSession)
	                validSession = (totalTime == maxSessionTimeLimit);
	            else
	                validSession = (totalTime >= minSessionTimeLimit && totalTime <= maxSessionTimeLimit);
	            
	            // If session is valid than add this session in the possible combination list and set all added talk as scheduled.
	            if(validSession) {
	                possibleCombinationsOfTalks.add(possibleCombinationList);
	                for(Talk talk : possibleCombinationList){
	                    talk.setScheduled(true);
	                }
	                possibleCombinationCount++;
	                if(possibleCombinationCount == totalPossibleTracks)
	                    break;
	            }
	        }
	        
	        return possibleCombinationsOfTalks;
	    }
	 
	 
	 /**
		* This method creates a list of Track Object given the List of scheduled morning and evening 
		* talk Objects. It computes and adds the scheduled time for morning and evening talk objects.
		* Also it adds Lunch and Networking Talk Objects to respective Tracks.
		* @param combForMornTalks    List of scheduled morning Talk Objects. 
		* @param combForEveTalks     List of scheduled evening Talk Objects.
		* @return List<Track>        List of final scheduled Track Obhects.
		* 
	*/
	 
	 private List<Track> getScheduledTalksList(List<List<Talk>> combForMornTalks, List<List<Talk>> combForEveTalks)
	    {
	        int totalPossibleTracks = combForMornTalks.size();
	        
	        List<Track> trackList = new ArrayList<Track>();
	        
	        // for loop to schedule event for tracks.
	        for(int trkCount = 0; trkCount < totalPossibleTracks; trkCount++) {
	        	
	        	Track track = new Track();
	        	track.setTrackId("Track" + " " +(trkCount+1)+ " ");
	        	Session session = new Session();
	           // List<Talk> talkList = new ArrayList<Talk>();
	            
	            // Create a date and initialize start time 09:00 AM.
	        	Calendar cal = Calendar.getInstance();
	        	cal.set(Calendar.HOUR_OF_DAY, 9);
	        	cal.set(Calendar.MINUTE, 0);
	        	cal.set(Calendar.SECOND, 0);
	        	Date date = cal.getTime();
	        	
	        	
	           SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");
	           String scheduledTime = dateFormat.format(date);
	           
	            
	            // Morning Session - set the scheduled time in the talk and get the next time using time duration of current talk.
	            List<Talk> mornSessionTalkList = combForMornTalks.get(trkCount);
	            for(Talk talk : mornSessionTalkList) {
	                talk.setScheduledTime(scheduledTime);
	                scheduledTime = getNextScheduledTime(date, talk.getTimeDuration());
	                
	            }
	            session.setMorningTalk(mornSessionTalkList);
	            
	            // Scheduled Lunch Time for 60 mins.
	            int lunchTimeDuration = 60;
	            Talk lunchTalk = new Talk("Lunch", "Lunch", 60);
	            lunchTalk.setScheduledTime(scheduledTime);
	            session.setLunchBreak(lunchTalk);
	            
	            // Evening Session - set the scheduled time in the talk and get the next time using time duration of current talk.
	            scheduledTime = getNextScheduledTime(date, lunchTimeDuration);
	            List<Talk> eveSessionTalkList = combForEveTalks.get(trkCount);
	            for(Talk talk : eveSessionTalkList) {
	                talk.setScheduledTime(scheduledTime);
	                scheduledTime = getNextScheduledTime(date, talk.getTimeDuration());
	            }
	            session.setEveningTalk(eveSessionTalkList);
	            
	            // Scheduled Networking Event at the end of session, Time duration is just to initialize the Talk object.
	            Talk networkingTalk = new Talk("Networking Event", "Networking Event", 60);
	            networkingTalk.setScheduledTime(scheduledTime);
	            session.setNetworkSession(networkingTalk);
	            
	            track.setSession(session);
	            trackList.add(track);
	        }
	        
	       
	        return trackList;
	    }
	 
	 /**
		* This method computes the next schedule time provided the previous talk 
		* time and duration of the Talk.
		* @param date            scheduled time of talk.
		* @param timeDuration    duration of talk.
		* @return String         scheduled time for next talk.
		* 
	*/
	 private String getNextScheduledTime(Date date, int timeDuration)
	    {
	        long timeInLong  = date.getTime();
	        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");
	        
	        long timeDurationInLong = timeDuration * 60 * 1000;
	        long newTimeInLong = timeInLong + timeDurationInLong;
	        
	        date.setTime(newTimeInLong);
	        String str = dateFormat.format(date);
	        return str;
	    }
	   
	 
	 /**
		* This method calculates the sum of all the Talk duration 
		* @param talkList List of validated Talk Data 
		* @return int sum of  Talk duration
		* 
	*/
    private  int getTotalTalksTime(List<Talk> talksList)
		{
		    if(talksList == null || talksList.isEmpty())
		    return 0;
		        
		    int totalTime = 0;
		    for(Talk talk : talksList) {
		        totalTime += talk.getTimeDuration();
		     }
		     return totalTime;
		}
		 
	
}
