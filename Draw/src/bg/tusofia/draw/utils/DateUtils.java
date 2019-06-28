package bg.tusofia.draw.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	//public static final String BORICA_CARD_FORMAT = "yyyyMM";
	public static final String FULL_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static final String BG_DOT_DATE = "dd.MM.yyyy";
	//-------------------------------------------------------------------------------
	public static String swapFromat(String time, String mask1, String mask2){
		String response;
		DateFormat df = new SimpleDateFormat(mask1);
		try {
			Date date = df.parse(time);
			response = new SimpleDateFormat(mask2).format(date);
		} catch (ParseException e) {
			response = time;
		}
		return response;
	}
	//-------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
    public static Date parseDate(String date, String mask){
        Date response;
        DateFormat df = new SimpleDateFormat(mask);
        try {
            response = df.parse(date);
        } catch (ParseException e) {
            response = null;
        }
        return response;
    }
    //-------------------------------------------------------------------------------
    public static String currentDateStr(String mask){
        String response;
        try {
            Date date = new Date();
            response = new SimpleDateFormat(mask).format(date);
        } catch (Exception e) {
            response = "";
        }
        return response;
    }
    //-------------------------------------------------------------------------------
    public static Date currentDate(){
        return new Date();
    }
    //-------------------------------------------------------------------------------
    public static long getDiffMinutes(Date date1, Date date2){
        long elapsedTime = -1;
        try {
            elapsedTime = date1.getTime() - date2.getTime();
            elapsedTime = Math.abs(elapsedTime);
            elapsedTime = (int)(elapsedTime/1000/60);
        } catch (Exception e){
            elapsedTime = -1;
        }
        return elapsedTime;

    }
    //-------------------------------------------------------------------------------
    public static long getDiffSeconds(Date date1, Date date2){
        long elapsedTime = -1;
        try {
            elapsedTime = date1.getTime() - date2.getTime();
            elapsedTime = Math.abs(elapsedTime);
            elapsedTime = elapsedTime/1000;
        } catch (Exception e){
            elapsedTime = -1;
        }
        return elapsedTime;
    }
  //-------------------------------------------------------------------------------
}
