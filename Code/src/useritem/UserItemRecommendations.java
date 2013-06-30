package useritem;

import datamining.RecordDataNoSimilarlyItems;
import datamining.TopProducts;
import datamining.UserPreferences;
import java.util.Arrays;
import java.util.TreeMap;

/**
 *
 * @author Wayne Rijsdijk
 */
public class UserItemRecommendations {
	public static TopProducts getRecommendations(TreeMap<Integer, UserPreferences> users, RecordDataDistance rdd) {
		int[] recommendationItems = new int[5];
		int recommendationCounter = 0;
		
		TopProducts tp = new TopProducts();
		
		UserPreferences thisUser = users.get(rdd.thisUser);
		int userCounter = 0;
		if(thisUser != null) {
			for(int userId : rdd.users) {
				System.out.println("User " + userId);
				
				UserPreferences otherUser = users.get(userId);
				float[] otherRatings = otherUser.getRatings();
				
				RecordDataNoSimilarlyItems rd = thisUser.noSimilarItems(otherUser.getItemIds(), otherUser.getRatings());
				
				int counter = 0;
				for(int itemId : rd.items) {
					if(!contains(recommendationItems, itemId)) {
						recommendationItems[recommendationCounter++] = itemId;
						tp.addProduct(itemId, rdd.distances[userCounter]*otherRatings[counter]);
						//System.out.println("RANKING user" + userId +  " | item: " + itemId + " | " + rdd.distances[userCounter]*otherRatings[counter]);
						if(recommendationCounter >= 5) {
							break;
						}
					}
					counter++;
				}
				
				if(recommendationCounter >= 5) {
					break;
				}
				
				userCounter++;
			}
		}
		else {
			//No recommendations
		}
		
		return tp;
		//return recommendationCounter < 5 ? Arrays.copyOf(recommendationItems, recommendationItems.length - (recommendationItems.length-recommendationCounter)) : recommendationItems;
	}
	
	public static boolean contains( final int[] array, final int v ) {
		for ( final int e : array )
			if ( e == v  )
				return true;

		return false;
	}
}
