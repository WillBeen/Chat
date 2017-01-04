package fr.willbeen.chatUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public static final int typeError = 0;
	public static final int typeWarning = 1;
	public static final int typeInfo = 2;
	
	private TextIOListener outputListener = null;
	
	public Logger(TextIOListener ol) {
		outputListener = ol;
	}
	
	public void log(int logType, String txt) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String logDate = df.format(new Date());
		String logMsg;
		switch (logType) {
		case typeError :
			logMsg = "ERROR : " + txt;
			break;
		case typeWarning :
			logMsg = "WARNING : " + txt;
			break;
		case typeInfo :
			logMsg = "INFO : " + txt;
			break;
		default :
				logMsg = "###";
		} 
		logMsg = logDate + " : " + logMsg;
//		System.out.println(logMsg);
		outputListener.processIO(logMsg);
	}
	
	public void log(String txt) {
		log(typeInfo, txt);
	}
	
	public TextIOListener getOutputListener() {
		return outputListener;
	}
}
