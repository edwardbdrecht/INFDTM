package recommendationStrategy;

import datamining.RecordDataSimilarlyItems;
import datamining.UserPreferences;
import java.util.Arrays;

/**
 *
 * @author Wayne Rijsdijk
 */
public class Euclideon implements IStrategy {
	@Override
	public float execute(UserPreferences user1, UserPreferences user2) {
		float distance = 0.0f;
		
		RecordDataSimilarlyItems rd = user1.similarItems(user2.getItemIds(), user2.getRatings());
		float[] ratingsUser1 = rd.ratings1;
		float[] ratingsUser2 = rd.ratings2;
		
		boolean hasMatches = ratingsUser1.length > 0;
		
		for(int i = 0; i < ratingsUser1.length; i++) {
			distance += Math.pow(Math.abs(ratingsUser1[i] - ratingsUser2[i]), 2);
		}
		
		return hasMatches ? (float) Math.sqrt(distance) : 999.0f;
	}
}