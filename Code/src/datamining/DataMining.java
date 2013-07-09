package datamining;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import useritem.CreateSimilarityMap;
import useritem.UserItemRecommendations;

public class DataMining {
	static TreeMap<Integer, UserPreferences> userPreferences;
	static TreeMap<Integer, Movie> movies;
	static TopProducts tp;
	
	public static void main(String[] args) {
		System.out.println("Please select a datafile");
		File dataFile = new ChooseFile().getFile();
		//File dataFile = new File("C:\\Users\\Wayne Rijsdijk\\Desktop\\datamining.txt");
		
		userPreferences = new TreeMap<Integer, UserPreferences>();
		tp = new TopProducts();
		
		int lineNumber = 0;
		StringTokenizer st;
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
				
				//System.out.println(itemId + " added");
				//tp.addProduct(itemId); //add item to topProducts class for creating top product lists
			}
			
			//DEBUG
			for(UserPreferences up : userPreferences.values()) {
				System.out.println(up.toString());
			}
			
			//int[] popularProducts = tp.getPopularProducts();
			//for(int popularProduct : popularProducts) {
			//	System.out.println(Integer.toString(popularProduct));
			//}
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
		
		CreateSimilarityMap c = new CreateSimilarityMap(userPreferences);
		//c.printSimilarityMap(c.getSimilarityMap());
		
		TopProducts recommendedItems = UserItemRecommendations.getRecommendations(userPreferences, c.getSimilarityMap().get(7));
		System.out.println("Recommended userItem items:");
		
		String[] recommendedItemz = recommendedItems.getPopularProducts(3);
		Collections.reverse(Arrays.asList(recommendedItemz));
		for(String recommendedItem : recommendedItemz) {
			System.out.println("Product ID: " + recommendedItem);
		}
		
		//RecordDataNoSimilarlyItems rd = userPreferences.get(17).noSimilarItems(userPreferences.get(15).getItemIds(), userPreferences.get(15).getRatings());
		//System.out.println("DEBUG");
		//for(int recommendedItem : rd.items) {
		//	System.out.println("Product ID: " + recommendedItem);
		//}
		
		//RecommendationDistanceStrategy rds = new RecommendationDistanceStrategy(new Euclideon());
		//float kaas = rds.getStrategy().execute(userPreferences.get(13), userPreferences.get(16));
		//System.out.println(kaas);
		
		System.exit(0);
	}
}