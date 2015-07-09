/**
* Interface that provides method for scheduling conference 
* for programming Talks..
*
*/

package com.conf.schedule;

import com.conf.exception.TrackScheduleException;

public interface IConferenceScheduler {

	/**
	* This method schedules the conference 
	* @param inputFileName name of the file containing the input data
	* @return Nothing
	* @exception TrackScheduleException
	*/
	public void scheduleConference(String inputFileName) throws TrackScheduleException;
}
