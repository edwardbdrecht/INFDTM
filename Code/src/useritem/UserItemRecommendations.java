package useritem;

import datamining.RecordDataNoSimilarlyItems;
import datamining.UserPreferences;
import java.util.Arrays;
import java.util.TreeMap;

/**
 *
 * @author Wayne Rijsdijk
 */
public class UserItemRecommendations {
	public static int[] getRecommendations(TreeMap<Integer, UserPreferences> users, RecordDataDistance rdd) {
		int[] recommendationItems = new int[5];
		int recommendationCounter = 0;
		
		UserPreferences thisUser = users.get(rdd.thisUser);
		if(thisUser != null) {
			for(int userId : rdd.users) {
				System.out.println("User " + userId);
				
				UserPreferences otherUser = users.get(userId);
				
				RecordDataNoSimilarlyItems rd = thisUser.noSimilarItems(otherUser.getItemIds(), otherUser.getRatings());
				
				for(int itemId : rd.items) {
					if(!contains(recommendationItems, itemId)) {
						recommendationItems[recommendationCounter++] = itemId;
						
						if(recommendationCounter >= 5) {
							break;
						}
					}
				}
				
				if(recommendationCounter >= 5) {
					break;
				}
			}
		}
		else {
			//No recommendations
		}
		
		return recommendationCounter < 5 ? Arrays.copyOf(recommendationItems, recommendationItems.length - (recommendationItems.length-recommendationCounter)) : recommendationItems;
	}
	
	public static boolean contains( final int[] array, final int v ) {
		for ( final int e : array )
			if ( e == v  )
				return true;

		return false;
	}
}
