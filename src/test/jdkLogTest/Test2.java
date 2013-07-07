package test.jdkLogTest;

import java.util.logging.Logger;

import logUtil.LogWriter;

public class Test2 {

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
		
		Logger log2 = lw.getLogWriter("C:\\Users\\qrks\\Desktop\\3333.txt", true);
		log2.info("wahahaha-222");
		
		
	}

}
