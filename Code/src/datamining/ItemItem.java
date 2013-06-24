package datamining;

import java.util.TreeMap;
import java.util.Arrays;

public class ItemItem 
{
    // Data
    private int[] userIds;
    private int[] itemIds;
    private float[][] ratings;
    
    // Build table with ratings
    public void buildRatingTable(TreeMap<Integer, UserPreferences> userPrefs)
    {
        // Instantiate everything
        userIds = new int[0];
        itemIds = new int[0];
        ratings = new float[0][0];
        
        // Fill the userIds and itemIds
        for(int i = 0; i < userPrefs.size(); i++)
        {
            // UserID
            this.userIds = this.addItemId(userPrefs.get(i+1).getUserId(), this.userIds);
            
            // ItemID
            for(int c = 0; c < userPrefs.get(i+1).getItemIds().length; c++)
            {
                this.itemIds = this.addItemId(userPrefs.get(i+1).getItemIds()[c], this.itemIds);
            }
        }
        
        // Give rating table it's new size
        ratings = new float[this.userIds.length][this.itemIds.length];
        
        // Fill the rating table
        for(int i = 0; i < userIds.length; i++)
        {
            for(int c = 0; c < userPrefs.get(i+1).getItemIds().length; c++)
            {
                int pos = Arrays.binarySearch(this.itemIds, userPrefs.get(i+1).getItemIds()[c]);
                ratings[i][pos] = userPrefs.get(i+1).getRatings()[c];
            }
        }
    }
    
    private int[] addItemId(int id, int[] sourceArr)
    {        
        int key = Arrays.binarySearch(sourceArr, id); //search in the itemId's array for a corresponding id.
        if (key >= 0) { //this item already exists. Just overwrite the ratings value now
                sourceArr[key] = id;
        }
        else { //new itemId
                key = Math.abs(key)-1; //define the spot in the array for the new item

                //get both arrays with 1 extra space for the new item
                int[] itemIdsCopy = Arrays.copyOf(sourceArr, sourceArr.length+1);

                //check if the position of the new item is at the end or not
                if(key+1 < itemIdsCopy.length) { //position is not at the end so we need to make space for the new element
                        System.arraycopy(sourceArr, key, itemIdsCopy, key+1, itemIdsCopy.length-key-1);
                }

                itemIdsCopy[key] = id;
                return itemIdsCopy;
        }
        return sourceArr;
    }
    
    public void printALl()
    {        
        // Table print
        System.out.print("   ");
        for(int a=0; a < itemIds.length; a++)
        {
            if(itemIds[a] < 10)
                System.out.print(itemIds[a]+"   ");
            else
                System.out.print(itemIds[a]+"  ");
        }
        System.out.println("");
        for(int i = 0; i < userIds.length; i++)
        {
            System.out.print(userIds[i] + "  ");
            for(int c=0; c < itemIds.length; c++)
            {
                System.out.print(ratings[i][c]+" ");
            }
            System.out.println("");
        }
    }
}
