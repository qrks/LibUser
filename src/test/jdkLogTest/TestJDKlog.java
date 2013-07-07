package test.jdkLogTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import logUtil.LogWriter;


public class TestJDKlog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestJDKlog t1 = new TestJDKlog();
		try {
			t1.test4();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void test4() throws Exception {
		LogWriter lw = new LogWriter();
		Logger logger = lw.getLogWriter("C:\\Users\\qrks\\Desktop\\3333.txt", true);
		logger.info("wahahaha-333");
		
		Thread.sleep(30*1000);
		
//		Logger log2 = lw.getLogWriter("C:\\Users\\qrks\\Desktop\\3333.txt", true);
//		log2.info("wahahaha-222");
		
		
	}
	
	void test3() throws Exception {
//		Logger log = getEnhancedLogWriter("aaa", "C:\\Users\\qrks\\Desktop\\2222.txt", true);
//		Logger log2 = getEnhancedLogWriter("aaa", "C:\\Users\\qrks\\Desktop\\3333.txt", true);
		
		Logger log3 = getLogWriter("C:\\Users\\qrks\\Desktop\\1111.txt", true);
		
		
		log3.info("wahwhwahawh");
	}
	
	void test2() throws SecurityException, IOException {
        Logger log = Logger.getLogger("TestJDKlog"); 
        log.setLevel(Level.INFO); 

//        ConsoleHandler consoleHandler = new ConsoleHandler(); 
//        consoleHandler.setLevel(Level.ALL); 
//        log.addHandler(consoleHandler); 
        
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        FileHandler fileHandler = new FileHandler("C:\\Users\\qrks\\Desktop\\" + d1 + ".log",true); 
        fileHandler.setLevel(Level.INFO); 
//        fileHandler.setFormatter(new MyLogHander()); 
        fileHandler.setFormatter(new SimpleFormatter()); 
        log.addHandler(fileHandler); 

        log.info("aaa"); 
        log.severe("wahahha");

	}
	
	void test1() throws SecurityException, IOException {
        Logger log = Logger.getLogger("lavasoft"); 
        log.setLevel(Level.INFO); 
        Logger log1 = Logger.getLogger("lavasoft"); 
        System.out.println(log == log1);     //true 
        Logger log2 = Logger.getLogger("lavasoft.blog"); 
//        log2.setLevel(Level.WARNING); 

        ConsoleHandler consoleHandler = new ConsoleHandler(); 
        consoleHandler.setLevel(Level.ALL); 
        log.addHandler(consoleHandler); 
        FileHandler fileHandler = new FileHandler("C:/testlog%g.log"); 
        fileHandler.setLevel(Level.INFO); 
        fileHandler.setFormatter(new MyLogHander()); 
        log.addHandler(fileHandler); 

        log.info("aaa"); 
        log2.info("bbb"); 
        log2.fine("fine"); 
	}
	
	private Logger getLogWriter(String fileName,boolean append) throws Exception {
//		String d1 = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		java.util.Date d = new java.util.Date();
		String logname = this.getClass().getSimpleName() + d.getTime();
		System.out.println(logname);
		return getEnhancedLogWriter(logname,fileName,append);
	}
	
	private Logger getEnhancedLogWriter(String loggerName,String fileName,boolean append) throws Exception {
        Logger log = Logger.getLogger(loggerName); 
        log.setLevel(Level.INFO); 
		
	      ConsoleHandler consoleHandler = new ConsoleHandler(); 
	      consoleHandler.setLevel(Level.ALL); 
	      log.addHandler(consoleHandler); 
        
        FileHandler fileHandler = new FileHandler(fileName,append); 
        fileHandler.setLevel(Level.INFO); 
//        fileHandler.setFormatter(new MyLogHander()); 
        fileHandler.setFormatter(new SimpleFormatter()); 
        log.addHandler(fileHandler); 

		return log;
		
	}
	
	
}


/*
 * SEVERE（最高值）
WARNING
INFO
CONFIG
FINE
FINER
FINEST（最低值）
 * */
class MyLogHander extends Formatter { 
    @Override 
    public String format(LogRecord record) { 
            return record.getLevel() + ":" + record.getMessage()+"\n"; 
    } 
}