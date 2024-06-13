package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Tools.Logs;

public abstract class Server {
	
	public ServerSocket serverSocket;
	public List<Socket> remoteClient;
	
	public ClientThread[] clientThreads;
	
	public int port;
	public int maxPlayer;
	
	public boolean isHost = false;
	
	PrintWriter out;
	BufferedReader in;
	
	public Server(int port, int maxPlayer) {
		this.port = port;
		this.maxPlayer = maxPlayer;
		
		remoteClient = new ArrayList<Socket>(maxPlayer);
		clientThreads = new ClientThread[maxPlayer];
	}
	
	public void startHosting() {
		if(isHost) return;
		
		try {
			serverSocket = new ServerSocket(this.port);
			serverSocket.setSoTimeout(10);
		}catch (IOException e) {
			new Logs("Error to start hosting", e, false);
			return;
		}
		
		isHost = true;
	}
	
	public void stopHosting() {
		if(!isHost) return;
		
		try {
			serverSocket.close();
		} catch (IOException e) {
			new Logs("Error to stop hosting", e, false);
			return;
		}
		
		isHost = false;
		serverSocket = null;
	}
	
	public abstract void update();
	
	public void connexionRequest() {
		if(!isHost) return;
		
		try {
			Socket tempSocket = serverSocket.accept();
			
			if(tempSocket != null && remoteClient.size() < maxPlayer) {
				remoteClient.add(tempSocket);
				System.out.println(tempSocket.getInetAddress() + " connected");
			}
			
		} catch (IOException e) {
			new Logs("Error to accept socket connection", e, false);
			return;
		}
	}
	
	public void connexionPing() {
		for(int i = 0; i < remoteClient.size(); i++) {
			if(clientThreads[i] != null && clientThreads[i].IsFinish()) {
				if(clientThreads[i].IsTimeout()) {
					System.err.println("/" + clientThreads[i].GetSocket().getInetAddress().getHostAddress() + " disconected");
					remoteClient.remove(clientThreads[i].GetSocket());
					clientThreads[i] = null;
				}else{
					System.out.println("Ping: " + clientThreads[i].GetPing() + " ms for " + clientThreads[i].GetSocket().getInetAddress().getHostAddress());
					clientThreads[i].run();
				}
			}else {
				clientThreads[i] = new ClientThread(remoteClient.get(i));
				clientThreads[i].run();
			}
		}
	}
	
	public void sendDataAll(String data) {
		for(int i = 0; i < remoteClient.size(); i++) {
			if(clientThreads[i] != null) {
				clientThreads[i].SendData(data);
			}
		}
	}
	
	public void sendData(String data, int socketID) {
		if(socketID < 0 || socketID >= maxPlayer || clientThreads[socketID] == null) return;
		clientThreads[socketID].SendData(data);
	}
	
	public void sendData(String data, String ip) {
		for(int i = 0; i < clientThreads.length; i++) {
			if(clientThreads[i] != null && clientThreads[i].GetSocket().getInetAddress().getHostAddress().contentEquals(ip)) {
				clientThreads[i].SendData(data);
			}
		}
	}
	
	public List<String> getDataAll() {
		List<String> datas = new ArrayList<String>();
		
		for(int i = 0; i < remoteClient.size(); i++) {
			if(clientThreads[i] != null) {
				List<String> data = clientThreads[i].RecieveData();
				if(data == null) continue;
				
				for(int j = 0; j < data.size(); j++) {
					datas.add(data.get(j));
				}
			}
		}
		
		return datas;
	}
	
}
