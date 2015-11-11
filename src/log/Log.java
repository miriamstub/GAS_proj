package log;

import org.apache.log4j.Logger;

public class Log {
	
	   static Logger logger = Logger.getLogger("GAS_proj");
	 
	   public static Logger getInstance() {
		   
		   return logger;
	   }
}
