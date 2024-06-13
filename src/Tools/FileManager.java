package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
	
	public static void createDirectory(String path, String name) {
		File file = new File(path + name);
		file.mkdir();
	}
	
	public static void createFile(String path, String name) {
		try {
			File file = new File(path + name);
			file.createNewFile();
		} catch (IOException e) {
			new Logs("Error for creating " + name + " in " + path, e, false);
			return;
		}
	}
	
	//-------------------------------\\
	
	public static String[] readFile(String path, String name) {
		try {
			File file = new File(path + name);
			Scanner scanner = new Scanner(file);
			String[] datas = new String[getFileLength(path, name)]; 
			int length = 0;
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				datas[length] = line;
				length++;
			}
			
			scanner.close();
			
			return datas;
		} catch (FileNotFoundException e) {
			new Logs("Error for find " + name + " in " + path, e, false);
			return null;
		}
	}
	
	public static void writeFile(String path, String name, String[] datas) {
		try {
			PrintWriter fileWriter = new PrintWriter(path + name);
			for(int i = 0; i < datas.length; i++) {
				fileWriter.println(datas[i]);
			}
			fileWriter.close();
		} catch (IOException e) {
			new Logs("Error duranting writing " + name + " in " + path, e, false);
			return;
		}
		
	}
	
	public static void writeFile(String path, String name, String[][] datas) {
		try {
			PrintWriter fileWriter = new PrintWriter(path + name);
			for(int i = 0; i < datas.length; i++) {
				for(int j = 0; j < datas[i].length; j++) {
					fileWriter.print(datas[i][j]);
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			new Logs("Error duranting writing " + name + " in " + path, e, false);
			return;
		}
		
	}
	
	//-------------------------------\\
	
	public static int getFileLength(String path, String name) {
		try {
			File file = new File(path + name);
			Scanner scanner = new Scanner(file);
			int length = 0;
					
			while(scanner.hasNextLine()) {
				scanner.nextLine();
				length++;
			}
			
			scanner.close();
			
			return length;
		} catch (FileNotFoundException e) {
			new Logs("Error to get a line in " + name + " in " + path, e, false);
			return 0;
		}
	}
	
	public static File[] getDirectoryLength(String path) {
		File file = new File(path);
		return file.listFiles();
	}
	
	//-------------------------------\\
	
	public static boolean exist(String path, String name) {
		File file = new File(path + name);
		
		return file.exists();
	}

}
