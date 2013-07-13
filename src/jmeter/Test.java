package jmeter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {

	int port = 12345;
	boolean stop = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test t = new Test();
		try {
			t.runTest();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void runTest() throws IOException {
		ServerSocket serSoc = new ServerSocket(port);
		int index = 1;
		while(!stop) {
			Socket s = serSoc.accept();
			System.out.println("index=" + index);
			ServerSocketThread sst1 = new ServerSocketThread(s);
			sst1.start();
			index += 1;
		}
	}
	
	
	class ServerSocketThread extends Thread {

		Socket s;
		InputStream is;
		OutputStream os;
		
		
		public ServerSocketThread(Socket soc) {
			this.s = soc;
		}
		
		@Override
		public void run() {
			try {
				
				getIOStream();
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
//				while((num = is.available()) > 0) {
//					byte[] buff = new byte[num];
//					is.read(buff);
//					recv += new String(buff);
//				}
				
				
				System.out.println("recv=[" + recv + "]");
				
				Thread.sleep(300);
				
				os.write("return success123".getBytes());
				os.flush();
				
				
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
