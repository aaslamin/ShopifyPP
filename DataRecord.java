import java.util.*;
import java.io.*;

public class DataRecord {
	
	private static final int DATA_CLENGTH = 14; // maximum number of characters allowed per record (for data field)
	public static final int RECORD_SIZE = (Integer.SIZE + (Character.SIZE*DATA_CLENGTH))/8; // total length of every record entry in bytes
	private int id;
	private String data;
	
	public DataRecord (int id, String data) {
		this.id = id;
		this.data = data;
	}
	
	public DataRecord () {
		this (0, null);
	}
	
	public void setID (int id) {
		this.id = id;	
	}
	
	public int getID () {
		return id;	
	}
	
	public void setData (String data) {
		this.data = data;	
	}
	
	public String getData () {
		return data;	
	}
	
	public void readFromFile (RandomAccessFile file) {
		try {
			id = file.readInt();
		} catch (IOException e) {
			System.err.println("invalid id");
			System.exit(1);
		}
		data = readString(file).replaceAll("\\s+","");
	}
	
	public void writeToFile (RandomAccessFile file) throws IOException {
		file.writeInt(id);
		writeString(file, data);
	}
	
	private String readString (RandomAccessFile file) {
		//String line = file.readLine();
		//System.out.println("line: " + line);
		char[] s = new char[DATA_CLENGTH];
		for (int i = 0; i < s.length; i++) {
			try {
				s[i] = file.readChar();
			} catch (IOException e) {
				System.err.println("invalid id");
				System.exit(1);
			}
			//System.out.println(i + ":" + s[i]);
		}
		return new String(s).replace('\0', ' '); 
	}
	
	private void writeString (RandomAccessFile file, String s) throws IOException {
		StringBuffer b = new StringBuffer(s);
		b.setLength(DATA_CLENGTH); // will force the data to be of specified length to ensure consistent record layout in datafile
		file.writeChars(b.toString());
	}
	
	@Override
	public String toString () {
		return "Record [id = " + id + ", value = " + data + "]";
	}
	
}
