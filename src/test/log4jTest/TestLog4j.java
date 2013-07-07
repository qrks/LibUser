package test.log4jTest;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * @author qrks
 * @date 2013-07-06
 * */
public class TestLog4j {

	static Logger logger = Logger.getLogger(TestLog4j.class.getName());

	public static void main(String[] args) {
		TestLog4j t1 = new TestLog4j();
		t1.test1();
	}

	private void test1() {
	      PropertyConfigurator.configure("E:\\Program Files\\eclipse-jee\\workspace1\\LibUser\\myLog4j.properties");
	      setLogFileName("C:\\Users\\qrks\\Desktop\\test111.log");
	      logger.info("Entering application.");
	      
	      logger.info("Exiting application.");
	}

	void test2() {
		// 使用默认的配置信息，不需要写log4j.properties
		BasicConfigurator.configure();
	}

	public void setLogFileName(String filename) {
		FileAppender appender = (FileAppender) Logger
				.getRootLogger().getAppender("a1");
		System.out.println(appender);
		appender.setFile(filename);// 动态地修改这个文件名
		appender.activateOptions();
	}

}
