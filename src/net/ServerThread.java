package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread {

	List socketList = new ArrayList();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ServerThread().test();
	}

	private void test() {
		try {
			System.out.println("start");
			
			System.out.println(socketList.size());
			Thread t1 = new SocketListener(5588);
			t1.start();
			
			System.out.println(socketList.size());
			
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void add2List(Socket soc) {
		socketList.add(soc);
	}
	
	
	public class SocketListener extends Thread {
		ServerSocket listenSock; // The socket to listen for connections
		int port;
		volatile boolean stop = false;

		public SocketListener(int port) throws IOException  {
			listenSock = new ServerSocket(port);
		}
		
		@Override
		public void run() {
			while (!stop) {
				try {
					Socket client = listenSock.accept();
					add2List(client);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		public void stopGo() {
			this.stop = true;
			this.interrupt();
			try {
				listenSock.close();
			} catch (IOException e) {
			} finally {
				if(listenSock != null)
					listenSock = null;
			}
		}
		
		private void serve() {
			
		}


	}
	
}


