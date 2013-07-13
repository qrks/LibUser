package pressure;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PressureTestClient {

	static int concurrencyNum = 1;  //并发进程数
	List threadList = new ArrayList();
	
	/**
	 * @param args
	 * 参数说明：
	 * 参数1： 并发进程数
	 * 参数2： 参数3的类型 ：    1字符串   2文件
	 * 参数3： 报文
	 * 参数4：host
	 * 参数5：port
	 * 示例：  100 1 msg 127.0.0.1 12345
	 * 
	 */
	public static void main(String[] args) {
		if(args.length != 5) {
			println("参数个数太少了  参数个数：5");
			return;
		}
		PressureTestClient ptc = new PressureTestClient();
		ptc.start(args);
		
	}
	
	private void start(String[] args) {
		concurrencyNum = Integer.parseInt(args[0]);
		int type = Integer.parseInt(args[1]);
		String msgImpl = args[2];
		String host = args[3];
		String port = args[4];
		if(type == 1) {
			for(int i=0; i<concurrencyNum; i++) {
				TestSocketThread tst = new TestSocketThread(host,Integer.parseInt(port),msgImpl);
				threadList.add(tst);
			}
			getThreadStart(threadList);
			
		}else if(type == 2) {
			String msg;
			msg = getFileContent(msgImpl);
			for(int i=0; i<concurrencyNum; i++) {
				TestSocketThread tst = new TestSocketThread(host,Integer.parseInt(port),msg);
				threadList.add(tst);
			}
			getThreadStart(threadList);

		}else {
			println("输入的第二个参数不合法");
			return;
		}
		
	}
	
	void getThreadStart(List l) {
		for(int j=0; j<threadList.size();j++) {
			TestSocketThread t = (TestSocketThread)threadList.get(j);
			t.start();
		}
	}
	
	static void println(String msg) {
		String ret = "";
		try {
			ret = new String(msg.getBytes("GBK"));
			System.out.println(ret);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	String getFileContent(String filename) {
		FileReader fr = null;
		String content = "";
		try {
			fr = new FileReader(new File(filename));
			char[] cbuf = new char[1024];
			int n;
			while((n = fr.read(cbuf)) > 0) { 
				content += new String(cbuf,0,n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fr != null)
					fr.close();
			} catch (Exception e) {
				fr = null;
			}
		}
		return content;
	}
	
	class TestSocketThread extends Thread {

		Socket s;
		InputStream is;
		OutputStream os;
		String sendMsg;
		
		public TestSocketThread(String host,int port,String msg) {
			try {
				s = new Socket(host, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.sendMsg = msg;
		}
		
		@Override
		public void run() {
			try {
				
				getIOStream();
				
				
				Thread.sleep(300);
				
				os.write(sendMsg.getBytes());
				os.flush();
				System.out.println();
				
				String recv = "";
				byte[] buff = new byte[1024];
				int num;
				for(;(num = is.read(buff)) != -1;) {
					if(num == 0) {
						break;
					}else {
						recv += new String(buff,0,num);
					}
					if(is.available() == 0) {
						break;
					}
					
				}
				
				System.out.println("recv=[" + recv + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(s != null)
						s.close();
				} catch (IOException e) {
					s = null;
				} 
				try {
					if(is != null)
						is.close();
				} catch (IOException e) {
					is = null;
				} 
				try {
					if(os != null)
						os.close();
				} catch (IOException e) {
					os = null;
				} 
			}
			
			
		}
		
		void getIOStream() throws IOException {
			this.is = s.getInputStream();
			this.os = s.getOutputStream();
		}
		
	}

}
