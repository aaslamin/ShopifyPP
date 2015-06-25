import java.io.*;
import java.util.*;

public class DBMS {
	
	private final static String filename = "ShopifyDB";
	private static RandomAccessFile DB;  // data file (each line contains a new entry)
	
	private final static File dataIndex = new File(".DATAINDEX_" + filename); // index file used for the 'list' command  (i.e. value lookups)
	
	private static int recordCount = 0;
	private static int recordStartOffset = Integer.SIZE/8; // offset from which data records are to be stored
	
	/* 
	 * Class that defines the command line interface (CLI):
	 * Each ENUM is associated with a Boolean value which determines whether or not that option requires an argument
	 * 
	 */
	
	public enum CLI {
		
		WRITE	 (true), 
		READ 	 (true),
		FIND	 (true),
		LIST	 (false); // i.e. does not require an argument 
		
		private final boolean reqArg; 
		
		CLI (boolean reqArg) {
			this.reqArg = reqArg;
		}
		
		public boolean requiresArgument () {
			return reqArg;
		}
		
		public static CLI fromString (String option) {
			if (option != null) {
				for (CLI opt : CLI.values()) {
					if (option.equalsIgnoreCase(opt.name())) return opt;
				}
			}
			printUsage((String.format("Invalid option (%s) provided", option)));
			return null;
		}
	}
	
	public static void initKeyIndex () {
		try {
			
			DB.seek(0);
			
			if (DB.length() == 0) {
				DB.writeInt(recordCount);
			} else {
				recordCount = DB.readInt();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void incrementKeyIndex () {
		
		try {
			
			DB.seek(0);
			DB.writeInt(++recordCount);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main (String[] args) {
				
		if (args.length < 1) {
			printUsage("At least one option is required");
		}
		
		
		CLI option = CLI.fromString(args[0]);
		if (option.requiresArgument() && args.length < 2) {
			printUsage(args[0] + " is missing an argument");
		}
		
		try {
			DB = new RandomAccessFile(filename, "rw");
			initKeyIndex();
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switch (option) {
		case FIND:
			find (args[1]);
			break;
		case LIST:
			list ();
			break;
		case READ:
			try {
				System.out.println(getRecord(Integer.parseInt(args[1])).toString());
			} catch (NumberFormatException e1) {
				System.err.println("invalid id");
				System.exit(1);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			break;
		case WRITE:
			try {
				insertRecord(new DataRecord(recordCount, args[1]));
				System.out.println("ID: " + (recordCount-1));
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		
		}		
	}
	
	public static void list () {
		
		DataRecord record = new DataRecord();
		try {
			DB.seek(recordStartOffset);
			while (DB.getFilePointer() < DB.length()) {
				record.readFromFile(DB);
				if (record != null) System.out.println(record.toString());
			}
		} catch (EOFException e1) {
			return;
		} catch (IOException e2) {
			return;
		}	
	}
	
	public static void find (String value) {
		System.out.println("Feature not implemented yet");
	}
	
	public static DataRecord getRecord (int id) throws IOException {
		if (id < 0) {
			System.out.println("invalid id: " + id);
			System.exit(1);
		}
		
		DataRecord record = new DataRecord();
		DB.seek(recordStartOffset + (id * DataRecord.RECORD_SIZE));
		record.readFromFile(DB);
		return record;
	}
	
	public static void insertRecord (DataRecord record) throws IllegalArgumentException, IOException {
		DB.seek(recordStartOffset + (record.getID() * DataRecord.RECORD_SIZE));
		record.writeToFile(DB);
		incrementKeyIndex();
	}
	
	
	public static void printUsage () {
		printUsage (null);
	}
	
	public static void printUsage (String message) {
		if (message != null) {
			System.err.println(message);
			System.err.println();
		}
		
        System.out.println("Usage: ");
        System.out.println("\t java DBMS write <DATA>");
        System.out.println("\t java DBMS list");
        System.out.println("\t java DBMS read <KEY>");
        System.out.println("\t java DBMS find <DATA>");
        
        System.exit(1);

	}
}





//
//
//public class DBMS {	
//	
//	static String filename;
//	public DBMS (String fname) {
//		
//		
//		
//		filename = fname;
//		File db = new File(filename);
//		
//		if(!db.exists()) {
//		    try {
//				db.createNewFile();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} 
//		try {
//			FileOutputStream oFile = new FileOutputStream(db, true);
//			oFile.close();
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	}
//	
//	public static void main (String[] args) {
//		DBMS db = new DBMS("22222");
//		
//		//writeReq("111111111111");
//		
//		
//		if (args.length < 1) {
//			System.out.println("dfsfsdf");
//			System.exit(1);
//		}
//		
//		if (args[0].equalsIgnoreCase("write")) {
//			int key = db.writeReq(args[1]);
//			System.out.println("got here " + args[1]);
//			
//		}
//		
//		if (args[0].equalsIgnoreCase("list")) {
//		}
//		
//		
//		if (args[0].equalsIgnoreCase("read")) {
//		
//		}
//		
//	}
//	
//	
//	public static int writeReq (String data) {
//		
//		try {
//			
//		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
//		    out.println("the text");
//		    out.close();
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	
//		return 0;
//	}


//public static void write (String data) {
//	
//	try {
//		
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
//		out.println(data);
//		out.close();
//		
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//	
//}
//
//public static void read (int primaryKey) {
//	
//}
//}
