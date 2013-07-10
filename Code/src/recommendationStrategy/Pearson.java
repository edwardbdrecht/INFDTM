package recommendationStrategy;

import datamining.RecordDataSimilarlyItems;
import datamining.UserPreferences;

/**
 *
 * @author Wayne Rijsdijk
 */
public class Pearson implements IStrategy {
	@Override
	public float execute(UserPreferences user1, UserPreferences user2) {
		float distance = 0.0f;
		
		RecordDataSimilarlyItems rd = user1.similarItems(user2.getItemIds(), user2.getRatings());
		float avg1 = CalculateAVG(rd.ratings1);
		float avg2 = CalculateAVG(rd.ratings2);
		
		boolean hasMatches = rd.ratings1.length > 0;
		
		float answer = 0;
		float som = 0;

		double wortel1 = 0;
		double wortel2 = 0;
		double tempAnswer = 0;
		for (int i = 0; i < rd.items.length; i++)
		{
			som += ((rd.ratings1[i] - avg1) * (rd.ratings2[i] - avg2));
			wortel1 += ((rd.ratings1[i] - avg1) * (rd.ratings1[i] - avg1));
			wortel2 += ((rd.ratings2[i] - avg2) * (rd.ratings2[i] - avg2));
	   }

	   tempAnswer = (som / (Math.sqrt(wortel1) * Math.sqrt(wortel2)));
	   answer = (float)tempAnswer;
	   
		return hasMatches ? (float) answer : 999.0f;
	}
	
	public float CalculateAVG(float[] ratings)
	{
		float AvgRating = 0;
		for( float rating : ratings)
		{
			AvgRating += rating;
		}
		return (AvgRating / ratings.length);
	 }
}