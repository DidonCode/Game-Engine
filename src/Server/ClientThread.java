package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Interface.Screen;

public class ClientThread implements Runnable {
	
	Socket remoteClient;
	
	PrintWriter out;
	BufferedReader in;
	
	boolean timeout;
	boolean finish;
	float ping = 0;
	
	public ClientThread(Socket remoteClient) {
		this.remoteClient = remoteClient;
		this.timeout = false;
		this.ping = 0;
		
		try {
			out = new PrintWriter(remoteClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(remoteClient.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		boolean send = false;
		long start = 0, end = 0;
		try {
			if(!send) {
				out.println("ping");
				start = Screen.getCurrentTime();
				send = true;
			}
			
			while(send) {
				end = Screen.getCurrentTime();
				ping = end - start;
				
				if(ping > 500) {
					send = false;
					timeout = true;
				}
				
				if(in.ready()) {
					if(in.readLine().contentEquals("pong")) {
						send = false;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finish = true;
	}
	
	public void SendData(String data) {
		out.println(data);
	}
	
	public List<String> RecieveData() {
		List<String> datas = new ArrayList<String>();
		
		try {
			while(in.ready()) {
				datas.add(in.readLine());
			}
			return datas;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean IsFinish() {
		return finish;
	}
	
	public float GetPing() {
		return ping;
	}
	
	public boolean IsTimeout() {
		return timeout;
	}
	
	public Socket GetSocket() {
		return remoteClient;
	}

}
