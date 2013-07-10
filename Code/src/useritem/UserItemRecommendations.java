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
		int[] recommendationItems = new int[0];
		
		TopProducts tp = new TopProducts();
		
		UserPreferences thisUser = users.get(rdd.thisUser);
		float[] sumpears;
		float[] sumranks;
		if(thisUser != null) {
			for(int userId : rdd.users) {
				UserPreferences otherUser = users.get(userId);
				//float[] otherRatings = otherUser.getRatings();
				
				RecordDataNoSimilarlyItems rd = thisUser.noSimilarItems(otherUser.getItemIds(), otherUser.getRatings());
				
				for(int itemId : rd.items) {
					
					
					if(recommendationItems.length <= 0) { //No elements exist yet. Make new arrays and add the values
						recommendationItems = new int[]{itemId};
					}
					else {
						int key = Arrays.binarySearch(recommendationItems, itemId); //search in the itemId's array for a corresponding id.
						if (key < 0) { //new itemId
							key = Math.abs(key)-1; //define the spot in the array for the new item

							//get both arrays with 1 extra space for the new item
							int[] itemIdsCopy = Arrays.copyOf(recommendationItems, recommendationItems.length+1);

							//check if the position of the new item is at the end or not
							if(key+1 < itemIdsCopy.length) { //position is not at the end so we need to make space for the new element
								System.arraycopy(recommendationItems, key, itemIdsCopy, key+1, itemIdsCopy.length-key-1);
							}

							itemIdsCopy[key] = itemId;

							recommendationItems = itemIdsCopy;
						}
					}
				}
			}
			
			sumranks = new float[recommendationItems.length];
			sumpears = new float[recommendationItems.length];
			for(int i = 0; i < rdd.users.length; i++) {
				int userId = rdd.users[i];
				UserPreferences otherUser = users.get(userId);
				for(int j = 0; j < recommendationItems.length; j++){
					float thisRank = rdd.distances[i] * otherUser.getRatingForItemId(recommendationItems[j]);
					if(thisRank > 0) {
						sumpears[j] += rdd.distances[i];
					}
					sumranks[j] += thisRank;
					System.out.println("user:" + userId + " - item:" + recommendationItems[j] + " - ranking: " + thisRank);
				}
			}
			
			for(int j = 0; j < recommendationItems.length; j++){
				if(recommendationItems[j] > 0) {
					tp.addProduct(recommendationItems[j], sumranks[j]/sumpears[j]);
					System.out.println("Recommend item: " + recommendationItems[j] + " | Predicted rank: " + sumranks[j]/sumpears[j] + " (" + sumranks[j] + "/" + sumpears[j] + ")");
				}
			}
				
				/*
				int counter = 0;
				for(int itemId : rd.items) {
					if(!contains(recommendationItems, itemId)) {
						recommendationItems[recommendationCounter++] = itemId;
						tp.addProduct(itemId, rdd.distances[userCounter]*otherRatings[counter]);
						System.out.println("RANKING user" + userId +  " | item: " + itemId + " | " + rdd.distances[userCounter]*otherRatings[counter]);
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
			*/
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
