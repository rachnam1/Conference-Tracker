/**
* Customized exception class for any scheduling error .
*
*/
package com.conf.exception;
public class TrackScheduleException extends Exception{
	
    private static final long serialVersionUID = 664456874499611218L;

    private String errorCode="Unknown_Exception";
        
    public TrackScheduleException(String message, String errorCode){
	     super(message);
	     this.errorCode=errorCode;
	}
	    
	public TrackScheduleException(String msg) {
	        super(msg);
	}
	
	public String getErrorCode(){
	        return this.errorCode;
	 }
}
