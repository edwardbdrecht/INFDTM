/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oneslope;

import java.util.Arrays;

/**
 *
 * @author Edward
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
	
	public int getUserId() {
		return this.userId;
	}
	
	public int[] getItemIds() {
		return this.itemIds;
	}
	
	public float[] getRatings() {
		return this.ratings;
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
