package test;

import logUtil.SimpleLogWriter;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Test t = new Test();
		t.test1();
	}
	
	void test1() throws Exception {
		SimpleLogWriter slw = new SimpleLogWriter("C:\\Users\\qrks\\Desktop\\3333.txt", true);
		SimpleLogWriter slw2 = new SimpleLogWriter("C:\\Users\\qrks\\Desktop\\3333.txt", true);
		slw.log("�����ҵ�logUitl������");
		slw2.log("�����ҵ�logUitl4������");
		slw.log("�����ҵ�logUitl2������");
		slw2.log("�����ҵ�logUitl5������");
		slw.log("�����ҵ�logUitl3������");
		slw2.logBoth("�����ҵ�logUitl6������");
	}

}
