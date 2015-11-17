package Deserializer;

import global.Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import log.Log;
import API.GeneratorAPI;
import Model.DateFormats;
import Model.Event;
import Model.EventType;
import Model.SchedulerInfoType;
import Model.SchDay;
import Model.ValidateUtils;

/**
 * <img src="file.png" />
 */
public class CCMSDeserializer implements IDeserializer{

       private static CCMSDeserializer instance = new CCMSDeserializer();

       private CCMSDeserializer(){}

       public static CCMSDeserializer getInstance(){

              return instance;     
       }

       Date date, time, start, dur, length;
       int brk, pos;
       String adName;
       EventType eventType;
       Logger log = Log.getInstance();

       public boolean validAndConvertEventDataParams(String[] rowObjs){     

              if(rowObjs.length < DeserializerConfiguration.CCMS_MIN_PARAMETERS)
                     return false;

              ValidateUtils.setIProperties(SchedulerInfoType.CCMS);

              date = ValidateUtils.getDate(rowObjs[DeserializerConfiguration.CCMS_DATE_LOCATION]);
              time = ValidateUtils.getTime(rowObjs[DeserializerConfiguration.CCMS_TIME_LOCATION]);
              start = ValidateUtils.getStart(rowObjs[DeserializerConfiguration.CCMS_START_LOCATION]);
              dur = ValidateUtils.getDuration(rowObjs[DeserializerConfiguration.CCMS_DURATION_LOCATION]);
              brk = ValidateUtils.getBrk(rowObjs[DeserializerConfiguration.CCMS_BRK_LOCATION]);
              pos = ValidateUtils.getPos(rowObjs[DeserializerConfiguration.CCMS_POS_LOCATION]);
              length = ValidateUtils.getLength(rowObjs[DeserializerConfiguration.CCMS_LENGTH_LOCATION]);
              adName = ValidateUtils.getAdName(rowObjs[DeserializerConfiguration.CCMS_ADNAME_LOCATION]);

              if(rowObjs.length < DeserializerConfiguration.CCMS_MAX_PARAMETERS){
                     eventType = EventType.SCHEDULED;
              }
              else{
                     eventType = ValidateUtils.getEventType(rowObjs[DeserializerConfiguration.CCMS_MAX_PARAMETERS -1]);
              }

              return validateData(rowObjs);

       }

       public boolean validateData(String[] rowObjs) {

              boolean fReturn = true;

              if (!ValidateUtils.isValidActualTime(rowObjs[8])){
                     fReturn = false;
                     log.error("ActualTime invalid digits");
              }
              else if(!ValidateUtils.isValidActualLength(rowObjs[9])){
                     fReturn = false;
                     log.error("ActualLength invalid digits");
              }

              else if(!ValidateUtils.isValidActualPos(rowObjs[10])){
                     fReturn = false;
                     log.error("ActualPos invalid digits");
              }

              else if(!ValidateUtils.isValidStatusCode(rowObjs[12])){
                     fReturn = false;
                     log.error("StatusCode invalid digits");
              }

              else if(!ValidateUtils.notNull(date ,time,start ,dur ,brk,pos ,length,adName ,eventType)){
                     fReturn = false;
                     log.error("Invalid event data");
              }

              return fReturn;
       }

       public void run(){

              BufferedReader br = null;
              ValidateUtils.setIProperties(SchedulerInfoType.CCMS);

              try {

                     String sCurrentLine;
                     File folder = new File(DeserializerConfiguration.FOLDER_DESERIALIZER_PATH);
                     Date date = null;

                     for (File fileEntry : folder.listFiles()) {                                
                           String schName = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf("."));
                           if(ValidateUtils.isValidSchedulerName(schName)){
                                  SchDay mySchDay = new SchDay(schName, SchedulerInfoType.CCMS, schName.substring(0, 3), schName.substring(3, 5),schName.substring(5, 8));
                                  try {
                                         //check the file name
                                         date = new SimpleDateFormat(DateFormats.MMdd.toString()).parse(Integer.parseInt(fileEntry.getName().substring(0, 1), 16) + fileEntry.getName().substring(1, 3));

                                  } catch (ParseException e) {
                                         e.printStackTrace();
                                  }

                                  //create the file
                                  Manager.getInstance().addSchedulerInfo(mySchDay);

                                  log.info("New CCMS Scheduled Info " + mySchDay.getSchInfoName() + " created successfully");

                                  br = new BufferedReader(new FileReader(fileEntry.getPath()));
                                  while ((sCurrentLine = br.readLine()) != null) {

                                         String[] rowObjs = sCurrentLine.split("\\s+");

                                         if(!rowObjs[0].equals("REM")){
                                                if(validAndConvertEventDataParams(rowObjs)){
                                                       Event event = new Event(date, time, start, dur, brk, pos, date, sCurrentLine, eventType); 
                                                       GeneratorAPI.createEvent(event, mySchDay);
                                                }      

                                         }
                                  }
                           }
                           else
                                  Log.getInstance().error("Can not create CCMS Scheduled Info " + fileEntry.getName() + ". SchedulerInfo name is invalid");
                     }

              } catch (IOException e) {
                     e.printStackTrace();
              } finally {
                     try {
                           if (br != null)br.close();
                     } catch (IOException ex) {
                           ex.printStackTrace();
                     }
              }
       }
}


