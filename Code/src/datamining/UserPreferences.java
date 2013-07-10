package datamining;

import java.util.Arrays;

/**
 * @author Wayne Rijsdijk
 */
public class UserPreferences {
	private int userId;
	private int[] itemIds;
	private float[] ratings;
	
	//cannot instantiate without userID
	private UserPreferences() {}
	
	public UserPreferences(int userId) {
		this.userId = userId;
		this.itemIds = new int[0];
		this.ratings = new float[0]; //geen double gezien we geen superprecisie nodig hebben, maar wel een laag geheugenverbruik
	}
	
	public void addElement(int itemId, float rating) {
		if(itemIds.length <= 0) { //No elements exist yet. Make new arrays and add the values
			this.itemIds = new int[]{itemId};
			this.ratings = new float[]{rating};
		}
		else {
			int key = Arrays.binarySearch(this.itemIds, itemId); //search in the itemId's array for a corresponding id.
			if (key >= 0) { //this item already exists. Just overwrite the ratings value now
				this.ratings[key] = rating;
			}
			else { //new itemId
				key = Math.abs(key)-1; //define the spot in the array for the new item
				
				//get both arrays with 1 extra space for the new item
				int[] itemIdsCopy = Arrays.copyOf(this.itemIds, this.itemIds.length+1);
				float[] ratingsCopy = Arrays.copyOf(this.ratings, this.ratings.length+1);
				
				//check if the position of the new item is at the end or not
				if(key+1 < itemIdsCopy.length) { //position is not at the end so we need to make space for the new element
					System.arraycopy(this.itemIds, key, itemIdsCopy, key+1, itemIdsCopy.length-key-1);
					System.arraycopy(this.ratings, key, ratingsCopy, key+1, ratingsCopy.length-key-1);
				}
				
				itemIdsCopy[key] = itemId;
				ratingsCopy[key] = rating;
				
				this.itemIds = itemIdsCopy;
				this.ratings = ratingsCopy;
			}
		}
	}
	
	public RecordDataSimilarlyItems similarItems(int[] itemsUser2, float[] ratingsUser2) {
		int[] similarItems = new int[this.itemIds.length];
		float[] userRatings1 = new float[this.itemIds.length];
		float[] userRatings2 = new float[this.itemIds.length];
		int counter = 0;
		
		for (int i = 0; i < this.itemIds.length; i++) {
			for (int j = 0; j < itemsUser2.length; j++) {
				if(this.itemIds[i] == itemsUser2[j]) {
					similarItems[counter] = this.itemIds[i];
					userRatings1[counter] = this.ratings[i];
					userRatings2[counter] = ratingsUser2[j];
					counter++;
				}
			}
		}
		
		System.arraycopy(similarItems, 0, similarItems, 0, counter);
		System.arraycopy(userRatings1, 0, userRatings1, 0, counter);
		System.arraycopy(userRatings2, 0, userRatings2, 0, counter);
		
		RecordDataSimilarlyItems rd = new RecordDataSimilarlyItems();
		rd.items = similarItems;
		rd.ratings1 = userRatings1;
		rd.ratings2 = userRatings2;
		
		return rd;
	}
	
	public RecordDataNoSimilarlyItems noSimilarItems(int[] itemsUser2, float[] ratingsUser2) {
		boolean existsInBoth;
		int[] noSimilarItems = new int[this.itemIds.length];
		float[] ratings = new float[this.itemIds.length];
		int counter = 0;
		
		for (int i = 0; i < itemsUser2.length; i++) {
			existsInBoth = false;
			
			for (int j = 0; j < this.itemIds.length; j++) {
				if(this.itemIds[j] == itemsUser2[i]) {
					existsInBoth = true;
					break;
				}
			}
			
			if(!existsInBoth) {
				noSimilarItems[counter] = itemsUser2[i];
				ratings[counter] = ratingsUser2[i];
				counter++;
			}
		}
		
		System.arraycopy(noSimilarItems, 0, noSimilarItems, 0, counter);
		System.arraycopy(ratings, 0, ratings, 0, counter);
		
		RecordDataNoSimilarlyItems rd = new RecordDataNoSimilarlyItems();
		rd.items = noSimilarItems;
		rd.ratings = ratings;
		
		return rd;
	}
	
	public float getRatingForItemId(int itemId) {
		int key = this.find(this.itemIds, itemId);
		if(key >= 0) {
			return this.ratings[key];
		}
		return 0.0f;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public int[] getItemIds() {
		return this.itemIds;
	}
	
	public float[] getRatings() {
		return this.ratings;
	}
	
	private int find(int[] array, int value) {
		for(int i=0; i<array.length; i++) {
			 if(array[i] == value) {
				 return i;
			 }
		}
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ratings for user ");
		builder.append(this.userId);
		builder.append("\n--------------------\n");
		for(int n = 0; n < this.itemIds.length; n++) {
			builder.append("(");
			builder.append(this.itemIds[n]);
			builder.append(",");
			builder.append(this.ratings[n]);
			builder.append(") ");
		}
		return builder.toString();
	}
}
