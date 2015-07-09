
/**
* The FileReader class reads the input file 
*
*/

package com.conf.util;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.conf.constants.TrackConstants;
import com.conf.exception.TrackScheduleException;

public class FileReader {

	/**
	* This method reads the input file given the filename and generates a list
	* of String objects from it. 
	* @param filename        name of the file to be read
	* @return List<String>   List computed from the content read from the file.
	* @throws TrackScheduleException 
	* 
	*/
	public List<String> readInput(String filename) throws TrackScheduleException {
		
		String basePath = TrackConstants.RESOURCE_PATH;
		String inputFileName = null;
		DataInputStream in = null;
		List<String> inputList = new ArrayList<String>();
		
		try{

			if (filename != null &&  !filename.trim().equals("")) {
				inputFileName = basePath+filename;
			}
			
			// Open the file.
	        FileInputStream fstream = new FileInputStream(inputFileName);
	        // Get the object of DataInputStream
	        in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String strLine = br.readLine();
	        
	        //Read File Line By Line
	        while (strLine != null)   {
	          inputList.add(strLine);
	          strLine = br.readLine();
	        }
	        
	       
      }catch (FileNotFoundException e) {
          throw new TrackScheduleException(e.getMessage(),"FILE_NOT_FOUND_EXCEPTION");
      }catch (IOException e) {
          throw new TrackScheduleException(e.getMessage(),"ERROR_PROCESSING_FILE_EXCEPTION");
      }finally{
          try {
              if(in !=null)in.close();
          } catch (IOException e) {
              throw new TrackScheduleException(e.getMessage(),"FILE_CLOSE_EXCEPTION");
          }
      }
		return inputList;
	}
}
