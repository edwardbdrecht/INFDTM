package recommendationStrategy;

import datamining.UserPreferences;
import java.util.Arrays;

/**
 * @author Wayne Rijsdijk
 */
public class Manhatten implements IStrategy {
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
				distance += Math.abs(itemsUser1[items] - itemsUser2[index]);
			}
		}
		
		return hasMatches ? distance : 999.0f;
	}
}
