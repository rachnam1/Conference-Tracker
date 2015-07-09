Steps to execute :
1) Sample data files are kept in resources folder. ConferenceData.txt has valid input data whereas ConferenceData_Invalid.txt has
invalid data. Keep those files in D:// drive. 
2) Inside Client folder, run TestConferenceManager.java as java application.
3) Output Data would be displayed on console.
4) Alternately, junits can be executed kept under test folder.


Design:
1) Read the input file, and create a List<String> object . Each string represents individual Talk.
2) Validate the List<String> object . Each object is validated to check conditions like:
   a) Talk title exists.
   b) Duration of talk should be either in min or lightning.
   After validation List<Talk> objects are created.
3)Calculate the total possible tracks possible.(totalPossibleTracks = totalTalksTime/perDayMinTime).
4)Compute the total possible combinations individually for morning and Evening Talk session. It is calculated as below:
  a) Iterate through the talkList and add those talks in (morning/evening) session,if the current talk time is less than
      maxSessionTimeLimit or sum of the current time and total of talk time added in list is less than maxSessionTimeLimit.
      This would create single morning/evening session. Add these into Track list considering the number of tracks possible 
      as calculated from Step 3.
5)After morning and evening talk have been computed, check if talk list is empty and try to accomodate them in evening talk.
  (with condition provided that it should not cross 5pm).
6) Finally create the Track List , with each track containing morning talk, lunch, evening talk and networking session.
7) Print Track List.


Folder/Package Structure:
1) com.conf.constants has the constants file.
2) com.conf.exception has the custom exception class.
3) com.conf.model has model classes like Talk, Track and session.
4) com.conf.track.schedulinglogic has interface and implementation for track scheduling
5) com.conf.schedule has the interface to the client for conference scheduling
6) com.conf.scheduleimpl has the implementation for conference scheduling.
7) com.conf.util has util class like FileReading


 
 
