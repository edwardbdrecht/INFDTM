package recommendationStrategy;

import datamining.UserPreferences;
import java.util.Arrays;

/**
 *
 * @author Wayne Rijsdijk
 */
public class Euclideon implements IStrategy {
	@Override
	public float execute(UserPreferences user1, UserPreferences user2) {
		int index; //for looking up the item in itemArray2
		
		boolean hasMatches = false;
		float distance = 0.0f;
		
		int[] itemsUser1 = user1.getItemIds();
		int[] itemsUser2 = user2.getItemIds();
		
		for(int items = 0; items < itemsUser1.length; items++) {
			index = Arrays.binarySearch(itemsUser2, itemsUser1[items]);
			if(index > 0) {
				hasMatches = true;
				distance += Math.pow(Math.abs(itemsUser1[items] - itemsUser2[index]), 2);
			}
		}
		
		return hasMatches ? (float) Math.sqrt(distance) : 999.0f;
	}
}