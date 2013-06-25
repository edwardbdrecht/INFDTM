package recommendationStrategy;

import datamining.UserPreferences;

/**
 * @author Wayne Rijsdijk
 */
public interface IStrategy {
	public float execute(UserPreferences user1, UserPreferences user2);
}
