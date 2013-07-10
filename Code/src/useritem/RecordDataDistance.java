package useritem;

import java.util.Arrays;

/**
 *
 * @author Wayne Rijsdijk
 */
public class RecordDataDistance {
	public int thisUser;
	public int[] users;
	public float[] distances;
	
	public RecordDataDistance() {
		this.users = new int[0];
		this.distances = new float[0]; //geen double gezien we geen superprecisie nodig hebben, maar wel een laag geheugenverbruik
	}
	
	public void addElement(int user, float distance, int k) {
		if(this.users.length <= 0) { //No elements exist yet. Make new arrays and add the values
			this.users = new int[]{user};
			this.distances = new float[]{distance};
		}
		else {
			int key = Arrays.binarySearch(this.distances, distance); //search in the itemId's array for a corresponding id.
			if (key >= 0) { //this item already exists. Just overwrite the ratings value now
				this.distances[key] = distance;
			}
			else { //new itemId
				key = Math.abs(key)-1; //define the spot in the array for the new item
				
				//get both arrays with 1 extra space for the new item
				int[] usersCopy = Arrays.copyOf(this.users, this.users.length+1);
				float[] distancesCopy = Arrays.copyOf(this.distances, this.distances.length+1);
				
				//check if the position of the new item is at the end or not
				if(key+1 < usersCopy.length) { //position is not at the end so we need to make space for the new element
					System.arraycopy(this.users, key, usersCopy, key+1, usersCopy.length-key-1);
					System.arraycopy(this.distances, key, distancesCopy, key+1, distancesCopy.length-key-1);
				}
				
				usersCopy[key] = user;
				distancesCopy[key] = distance;
				
				if(usersCopy.length > k) {
					this.users = Arrays.copyOfRange(usersCopy, usersCopy.length-k, usersCopy.length);
					this.distances = Arrays.copyOfRange(distancesCopy, distancesCopy.length-k, distancesCopy.length);
					
					//this.users = Arrays.copyOf(usersCopy, usersCopy.length > k ? k : usersCopy.length);
					//this.distances = Arrays.copyOf(distancesCopy, distancesCopy.length > k ? k : distancesCopy.length);
				}
				else {
					this.users = usersCopy;
					this.distances = distancesCopy;
				}
			}
		}
	}
	
	public void addElement(int user, float distance) {
		this.addElement(user,distance,3);
	}
}