package datamining;

import java.util.TreeMap;
import java.util.Arrays;

public class ItemItem 
{
    // Data
    private int[] userIds;
    private int[] itemIds;
    private float[][] ratings;
    private float[][] oneSlope;
    
    /*
     * Build table with ratings
     */
    public void buildRatingTable(TreeMap<Integer, UserPreferences> userPrefs)
    {
        // Instantiate everything
        userIds = new int[0];
        itemIds = new int[0];
        ratings = new float[0][0];
        oneSlope = new float[0][0];
        
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
        
        // Give rating table its new size
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
        
        // Create the One Slope
        oneSlope = new float[this.itemIds.length][this.itemIds.length];
        for(int i = 0; i < this.itemIds.length; i++)
        {
            for(int c = 0; c < this.itemIds.length; c++)
            {
                float totalDif = 0.0f;
                int totalRatings = 0;
                for(int a = 0; a < userIds.length; a++)
                {
                    if(ratings[a][i] != 0.0 && ratings[a][c] != 0.0)
                    {
                        totalDif += ratings[a][c] - ratings[a][i];
                        totalRatings++;
                    }
                }
                if(totalRatings != 0)
                    totalDif = totalDif / totalRatings;
                oneSlope[i][c] = totalDif;
            }
        }
        
    }
    
    /*
     * Adds an item into an array at its proper position
     */
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
    
    /*
     * Prints all data. For debugging. FTW
     */
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
        
        // OneSlope print
        System.out.print("   ");
        for(int a=0; a < itemIds.length; a++)
        {
            if(itemIds[a] < 10)
                System.out.print(itemIds[a]+"   ");
            else
                System.out.print(itemIds[a]+"  ");
        }
        System.out.println("");
        for(int i = 0; i < itemIds.length; i++)
        {
            System.out.print(itemIds[i] + "  ");
            for(int c=0; c < itemIds.length; c++)
            {
                System.out.print(oneSlope[i][c]+" ");
            }
            System.out.println("");
        }
    }
}
