package datamining;

import java.util.TreeMap;
import java.util.Arrays;
import java.util.Map;

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
        for (Map.Entry<Integer, UserPreferences> entry : userPrefs.entrySet())
        {
            this.userIds = this.addItemId(entry.getValue().getUserId(), this.userIds);
            
            // ItemID
            for(int c = 0; c < entry.getValue().getItemIds().length; c++)
            {
                this.itemIds = this.addItemId(entry.getValue().getItemIds()[c], this.itemIds);
            }
        }
        
        // Give rating table its new size
        ratings = new float[this.userIds.length][this.itemIds.length];
        
        // Fill the rating table
        int count = 0;
        for (Map.Entry<Integer, UserPreferences> entry : userPrefs.entrySet())
        {
            for(int c = 0; c < entry.getValue().getItemIds().length; c++)
            {
                int pos = Arrays.binarySearch(this.itemIds, entry.getValue().getItemIds()[c]);
                ratings[count][pos] = entry.getValue().getRatings()[c];
            }
            count++;
        }
        
        /*
         * OneSlope algorithm
         * 
         * for every item i
         *  for every other item j
         *    for every user u expressing preference  for both i and j
         *      add the difference in u´s preference for i and j to an average
         */
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
    
    public int[] getRecommendation(int userId)
    {
        int[] res = new int[1];
        
        int pos = Arrays.binarySearch(this.userIds, userId);
        if(pos > -1)
        {
            // Get the user ratings and no ratings
            int[] hasItemIds = new int[0];
            float[] ratingForIds = new float[0];
            int[] doesNoteHaveItemIds = new int[0];
            for(int i = 0; i < itemIds.length; i++)
            {
                if(ratings[pos][i] != 0.0)
                {
                    hasItemIds = this.addItemId(i, hasItemIds);
                    ratingForIds = this.addItemId(ratings[pos][i], ratingForIds);
                    int[] t = new int[0]; 
                }
                else
                {
                    doesNoteHaveItemIds = this.addItemId(i, doesNoteHaveItemIds);
                } 
            }
            /*
             * Recommendation
             * 
             *  For every item i the user u expresses no preference for
             *      for every item j that user u expresses a preference for
             *        find the average preference difference between j and i
             *        add this diff to uâ€™s preference value for j
             *        add this to a running average
             *  return the top items, ranked by these averages
             */
            for(int c = 0; c < doesNoteHaveItemIds.length; c++)
            {
                float totalRating = 0.0f;
                int totalValidRatings = 0;
                for(int a = 0; a < hasItemIds.length; a++)
                {
                    float avaragePrefDif = oneSlope[hasItemIds[a]][doesNoteHaveItemIds[c]];
                    if(avaragePrefDif != 0.0)
                    {
                        float diffAndRating = avaragePrefDif + ratingForIds[a];
                        totalRating += diffAndRating;
                        totalValidRatings++;
                    }
                }
                totalRating = totalRating / totalValidRatings;
            }
        }
        
        return res;
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
     * Adds an item into an array at its proper position
     */
    private float[] addItemId(float id, float[] sourceArr)
    {        
        int key = Arrays.binarySearch(sourceArr, id); //search in the itemId's array for a corresponding id.
        if (key >= 0) { //this item already exists. Just overwrite the ratings value now
                sourceArr[key] = id;
        }
        else { //new itemId
                key = Math.abs(key)-1; //define the spot in the array for the new item

                //get both arrays with 1 extra space for the new item
                float[] itemIdsCopy = Arrays.copyOf(sourceArr, sourceArr.length+1);

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
