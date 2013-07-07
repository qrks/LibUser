package logUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLogWriter {

	static File logFile;
	boolean append;
	static FileWriter fw;
	static int byteCount=0;
	static Date dat = new java.util.Date();
	static String nextline = System.getProperty("line.separator");
	
	static {
    	Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread() {
            @Override
            public void run() {
               try {
            	   if(fw != null) {
            		   fw.flush();
            		   fw.close();
            	   }
            		   
               } catch(Exception e) {
            		   fw = null;
               }
            }
        });
	}
	
	public SimpleLogWriter(String filePath,boolean append) {
		logFile = new File(filePath);
		try {
			initLogFile();
			fw = new FileWriter(logFile,append);  
		} catch (Exception e) {
			System.out.println("init LogFile failed");
			e.printStackTrace();
		}
		
	}
	
	public void log(String msg) {
		String line = "";
		try {
			line = formatMsg(msg);
			fw.write(line);
			byteCount += line.length();
			checkFlush();
		} catch (Exception e) {
			System.out.println("log() error");
			e.printStackTrace();
		}
	}
	
	//also print to Console
	public void logBoth(String msg) {
		log(msg);
		System.out.println(msg);
	}
	
	private String formatMsg(String msg) throws UnsupportedEncodingException {
		String line = getTimestmp() + " " + msg;
		byte[] bs = (line+nextline).getBytes("GBK");
		return new String(bs);
	}
	
	private String getThreadInfo() {
		Thread th = Thread.currentThread();
		String name = th.getName();
		long id = th.getId();
		return name + " " + id;
	}
	
	private String getTimestmp() {
		long millis = System.currentTimeMillis(); 
		dat.setTime(millis);
		String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(dat);
		return d1; 
	}
	
	private void checkFlush() throws IOException {
		if(byteCount > 1024) {
			fw.flush();
			byteCount = 0;
		}
			
	}

	void initLogFile() throws Exception {
		if(logFile == null) {
			throw new Exception("logFile is null");
		}
		if(!logFile.exists()) {
			boolean b = logFile.createNewFile();
			if(!b) {
				throw new Exception("cannot create log file");
			}
		}
	}

}
