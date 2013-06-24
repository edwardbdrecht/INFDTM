package datamining;

import java.util.TreeMap;
import java.util.Arrays;

public class ItemItem 
{
    // Data
    private int[] userIds;
    private int[] itemIds;
    private int[][] ratings;
    
    // Build table with ratings
    public void buildRatingTable(TreeMap<Integer, UserPreferences> userPrefs)
    {
        if(userIds == null)
        {
            userIds = new int[0];
            itemIds = new int[0];
            ratings = new int[0][0];
        }
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
    
    @Override
    public String toString()
    {
        String output = "";
        
        output += "------- UserIDS -------";
        for(int i = 0; i < userIds.length; i++)
        {
            output += " "+userIds[i];
        }
        output += " ------- ItemIDS -------";
        for(int i = 0; i < itemIds.length; i++)
        {
            output += " "+itemIds[i];
        }
        
        return output;
    }
}
