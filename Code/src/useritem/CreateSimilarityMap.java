package useritem;

import datamining.UserPreferences;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import recommendationStrategy.*;

/**
 *
 * @author Wayne Rijsdijk
 */
public class CreateSimilarityMap {
	private HashMap<Integer, RecordDataDistance> distanceMap = new HashMap<Integer, RecordDataDistance>();
	private RecommendationDistanceStrategy rds = new RecommendationDistanceStrategy(new Euclideon());
	
	private CreateSimilarityMap() {} //no instantiation without param
	public CreateSimilarityMap(TreeMap<Integer, UserPreferences> userPreferences) {
		Set<Entry<Integer, UserPreferences>> entrySet = userPreferences.entrySet();
		Iterator<Entry<Integer, UserPreferences>> outerUserPrefsIterator = entrySet.iterator();
		
		while(outerUserPrefsIterator.hasNext()) {
			UserPreferences u1 = outerUserPrefsIterator.next().getValue();
			Iterator<Entry<Integer, UserPreferences>> innerUserPrefsIterator = entrySet.iterator();
			
			RecordDataDistance rdd = new RecordDataDistance();
			rdd.thisUser = u1.getUserId();
			int counter = 0;
			while(innerUserPrefsIterator.hasNext()) {
				UserPreferences u2 = innerUserPrefsIterator.next().getValue();
				
				if(u1.getUserId() != u2.getUserId()) {
					rdd.addElement(u2.getUserId(), rds.getStrategy().execute(u1, u2));
					
					counter++;
				}
			}
			
			if(counter > 0) {
				distanceMap.put(u1.getUserId(), rdd);
			}
		}
	}
	
	public HashMap<Integer, RecordDataDistance> getSimilarityMap() {
		return this.distanceMap;
	}
	
	public void printSimilarityMap(HashMap<Integer, RecordDataDistance> distances) {
		Set<Entry<Integer, RecordDataDistance>> entrySet = distances.entrySet();
		Iterator<Entry<Integer, RecordDataDistance>> iterator = entrySet.iterator();
		
		System.out.println("Similarity map:");
		while(iterator.hasNext()) {
			Entry<Integer, RecordDataDistance> entry = iterator.next();
			System.out.println("Information for user " + entry.getKey());
			RecordDataDistance rdd = entry.getValue();
			for(int i = 0; i < rdd.distances.length; i++) {
				System.out.print("(" + rdd.users[i] + "," + rdd.distances[i] + ")");
			}
			
			System.out.println("");
		}
	}
}