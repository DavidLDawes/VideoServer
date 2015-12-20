package com.virtualsoundnw.videoserver;

public class Debug {
	public static boolean debugEnabled = false;
	
	private static final int maxDebug = 65536;
	private static String debugLog = "";
	
	public static void log(String myLog)
	{
		if (debugEnabled) {
			debugLog = debugLog + myLog;
			if (debugLog.length() > maxDebug)
			{
				debugLog = debugLog.substring(maxDebug/4);
			}
		}
	}

	public static String getLog()
	{
		return debugLog;
	}
}
