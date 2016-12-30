package fr.willbeen.chatUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.willbeen.chatServer.OutputListener;

public class Logger {
	public static final int typeError = 0;
	public static final int typeWarning = 1;
	public static final int typeInfo = 2;
	
	private OutputListener outputListener = null;
	
	public Logger(OutputListener ol) {
		outputListener = ol;
	}
	
	public void log(int logType, String className, String method, String txt) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String logDate = df.format(new Date());
		String logMsg;
		String logContent = className + "\tMethod : " + method + "\n\t" + txt;
		switch (logType) {
		case typeError :
			logMsg = "ERROR : " + logContent;
			break;
		case typeWarning :
			logMsg = "WARNING : " + logContent;
			break;
		case typeInfo :
			logMsg = "INFO : " + txt;
			break;
		default :
				logMsg = "###";
		} 
		logMsg = logDate + " : " + logMsg;
//		System.out.println(logMsg);
		outputListener.consoleOutput(logMsg);
	}
	
	public void log(String txt) {
		log(typeInfo, "", "", txt);
	}
	
	public OutputListener getOutputListener() {
		return outputListener;
	}
}
