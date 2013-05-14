package datamining;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class DataMining {
	static TreeMap<Integer, UserPreferences> userPreferences;
	
	public static void main(String[] args) {
		System.out.println("Please select a datafile");
		File dataFile = new ChooseFile().getFile();
		
		userPreferences = new TreeMap<Integer, UserPreferences>();
		
		int lineNumber = 0;
		StringTokenizer st = null;
		Scanner scanner = null;
		//Use a scanner because a BufferedReader is not necessary for this type of datasets (?)
		try {
			scanner = new Scanner(dataFile);
			while(scanner.hasNextLine()) {
				lineNumber++;
				String nextLine = scanner.nextLine();
				if(nextLine == null) { //makes sure that no null value is passed to the StringTokenizer. This is a part of the data validation system.
					continue;
				}
				st = new StringTokenizer(nextLine, ","); //use StringTokenizer, because that's twice as fast as String.split() when using simple patterns
				
				int userId = Integer.parseInt(st.nextToken());
				int itemId = Integer.parseInt(st.nextToken());
				float rating = Float.parseFloat(st.nextToken());
				
				UserPreferences up = userPreferences.get(userId); //get userPreferences from userId out of the TreeMap
				if(up == null) { //check if the userPreferences was not existent in the TreeMap
					up = new UserPreferences(userId); //make new userPreferences
				}
				up.addElement(itemId, rating); //add element to userPreferences
				userPreferences.put(userId, up); //put it back in the TreeMap
			}
			
			//DEBUG
			for(UserPreferences up : userPreferences.values()) {
				System.out.println(up.toString());
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("The selected file could not be found!");
		}
		catch(NumberFormatException e) {
			System.out.println("It look likes the data is corrupted! (Line: " + lineNumber + ")");
			e.getMessage();
			e.getStackTrace();
		}
		catch(Exception e) {
			System.out.println("A unknown error occured: " + e.getMessage());
			e.getStackTrace();
		}
		finally {
			if(scanner != null) {
				scanner.close();
			}
		}
		
		System.exit(0);
	}
}