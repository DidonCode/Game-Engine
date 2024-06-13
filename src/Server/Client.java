package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Tools.Logs;

public abstract class Client {
	
	public Socket clientSocket;
	
	String host;
	int port;
	
	PrintWriter out;
	BufferedReader in;
	
	public boolean isClient = false;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() {
		if(isClient) return;
		
		try {
			clientSocket = new Socket(host, port);
		} catch (IOException e) {
			new Logs("Connection refused to " + this.host + ":" + this.port, e, false);
			return;
		}
		
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));		
		} catch (IOException e) {
			new Logs("Error to create data sender", e, false);
			return;
		}
		
		isClient = true;
	}
	
	public void leave() {
		if(!isClient) return;
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			new Logs("Error to close client socket", e, false);
			return;
		}
		
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			new Logs("Error to close data sender", e, false);
			return;
		}
		
		isClient = false;
		clientSocket = null;
	}
	
	public abstract void update();
	
	public boolean sendData(String data) {
		if(!isClient) return false;

		if(clientSocket != null) {
			out.println(data);
			return true;
		}
		
		return false;
	}
	
	public List<String> recieveData() {
		if(!isClient) return null;
		
		List<String> datas = new ArrayList<String>();
		
		try {
			while(in.ready()) {
				datas.add(in.readLine());
			}
			return datas;
		} catch (IOException e) {
			return null;
		}
	}
	
}
