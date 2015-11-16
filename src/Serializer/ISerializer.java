package Serializer;

import Model.SchedulerInfo;

public interface ISerializer {
	
	void run();
	CCMSSerializer getInstance();
	void createSchedulerInfo(boolean value, SchedulerInfo value1);

}
