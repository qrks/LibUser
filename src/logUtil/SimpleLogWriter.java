package logUtil;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLogWriter {

	File logFile;
	boolean append;
	static FileWriter fw;
	static int byteCount=0;
	Date dat = new java.util.Date();
	String nextline = System.getProperty("line.separator");
	
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
	
	public SimpleLogWriter(String filePath,boolean append) throws Exception {
		logFile = new File(filePath);
		initLogFile();
		fw = new FileWriter(logFile,append);
	}
	
	public void log(String msg) throws Exception {
		String line = getTimestmp() + " " + msg;
		byte[] bs = (line+nextline).getBytes("GBK");
		fw.write(new String(bs));
		byteCount += bs.length;
		checkFlush();
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
	
	private void checkFlush() throws Exception {
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
