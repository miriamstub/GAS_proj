package log;

import org.apache.log4j.Logger;

/**
 * The logger object to all the logs of the project
 * @author eelmisha
 *
 */
public class Log {
	
	   static Logger logger = Logger.getLogger("GAS_proj");
	 
	   public static Logger getInstance() {
		   
		   return logger;
	   }
}
