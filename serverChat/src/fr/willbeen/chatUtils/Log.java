package fr.willbeen.chatUtils;

public class Log {
	public static final int typeError = 0;
	public static final int typeWarning = 1;
	public static final int typeInfo = 2;
	
	public static void log(int logType, String className, String method, String txt) {
		String logMsg;
		switch (logType) {
		case typeError :
			logMsg = "ERROR : ";
			break;
		case typeWarning :
			logMsg = "WARNING : ";
			break;
		case typeInfo :
			logMsg = "INFO : ";
			break;
		default :
				logMsg = "###";
		}
		logMsg += className + "\tMethod : " + method + "\n\t" + txt; 
		System.out.println(logMsg);
	}
}
