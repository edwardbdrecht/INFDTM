package itemitem;

import Util.ArrayResize;
import datamining.UserPreferences;
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
     * SKIP RATING TABLE FOR FAST COMPUTING?
     */
    public void buildRatingTable(TreeMap<Integer, UserPreferences> userPrefs)
    {
        // Instantiate everything
        userIds = new int[0];
        itemIds = new int[0];
        ratings = new float[0][0];
        oneSlope = new float[0][0];
        
        // Fill the userIds and itemIds first. This way is WAAY faster
        for (Map.Entry<Integer, UserPreferences> entry : userPrefs.entrySet())
        {
            if(entry.getValue().getItemIds().length > 1)
            {
                this.userIds = ArrayResize.addItem(entry.getValue().getUserId(), this.userIds);
            
                // ItemID
                for(int c = 0; c < entry.getValue().getItemIds().length; c++)
                {
                    this.itemIds = ArrayResize.addItem(entry.getValue().getItemIds()[c], this.itemIds);
                }
            }
        }
                
        // Give rating table its new size
        ratings = new float[this.userIds.length][this.itemIds.length];
        
        // Fill the rating table
        int count = 0;
        for (Map.Entry<Integer, UserPreferences> entry : userPrefs.entrySet())
        {
            if(entry.getValue().getItemIds().length > 1)
            {
                for(int c = 0; c < entry.getValue().getItemIds().length; c++)
                {
                    int pos = Arrays.binarySearch(this.itemIds, entry.getValue().getItemIds()[c]);
                    ratings[count][pos] = entry.getValue().getRatings()[c];
                }
                count++;
            }
        }
    }
    
    public void createAndFillOneSlope()
    {
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
            System.out.println("Checking rating for itemID "+i);
        }
    }
    
    public RecommendationResult[] getRecommendation(int userId)
    {
        RecommendationResult[] res = new RecommendationResult[0];     
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
                    hasItemIds = ArrayResize.addItem(i, hasItemIds);
                    ratingForIds = ArrayResize.addItem(ratings[pos][i], ratingForIds);
                }
                else
                {
                    doesNoteHaveItemIds = ArrayResize.addItem(i, doesNoteHaveItemIds);
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
            
            // To be returned
            RecommendationResult[] recommendation = new RecommendationResult[0];
            
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
                RecommendationResult result = new RecommendationResult();
                result.setItemId(itemIds[doesNoteHaveItemIds[c]]);
                result.setRecomValue(totalRating);
                // Get a sorted array. Only use the result if calculated position belongs to top! 
                //(stores about 75% less data!) and only takes top 4
                recommendation = ArrayResize.addItem(result, recommendation, 4);
            }
            
            return recommendation;
        }
        
        return res;
    }
    
    /*
     * Adds an item to the Rating table and rebuilds the One Slope table
     */
    public void addItem(UserPreferences pref, boolean shouldUpdateOneSlope)
    {   
        int[] oldItemIds = Arrays.copyOf(this.itemIds, this.itemIds.length);
        // First add the new UserId and ItemId
        this.userIds = ArrayResize.addItem(pref.getUserId(), this.userIds);
        for(int c = 0; c < pref.getItemIds().length; c++)
        {
            this.itemIds = ArrayResize.addItem(pref.getItemIds()[c], this.itemIds);
        }
        
        float[][] tempArr = new float[this.userIds.length][this.itemIds.length];
        
        // Fill the new Rating table
        int posUserId = Arrays.binarySearch(this.userIds, pref.getUserId()); //search in the itemId's array for a corresponding id.
        if(posUserId >= 0)
        {
            for(int i = 0; i < this.userIds.length; i++)
            {
                int unknowns = 0;
                for(int c = 0; c < this.itemIds.length; c++)
                {
                    if(i == posUserId)
                    {
                        int pos = Arrays.binarySearch(pref.getItemIds(), this.itemIds[c]);
                        if(pos >= 0)
                        {
                            tempArr[i][c] = pref.getRatings()[pos];
                        }
                    }
                    else if(posUserId+1 < userIds.length)
                    {
                        int pos = Arrays.binarySearch(oldItemIds, this.itemIds[c]);
                        if(pos < 0)
                        {
                            unknowns++;
                            if(oldItemIds.length < unknowns)
                            {
                                if(i < posUserId)
                                {
                                    tempArr[i][c] = this.ratings[i][c-unknowns];
                                }
                                else
                                {
                                    tempArr[i][c] = this.ratings[i-1][c-unknowns];
                                }
                            }
                        }
                        else
                        {
                            if(i < posUserId)
                            {
                                tempArr[i][c] = this.ratings[i][c];
                            }
                            else
                            {
                                tempArr[i][c] = this.ratings[i-1][c];
                            }
                        }
                    }
                    else
                    {
                        int pos = Arrays.binarySearch(oldItemIds, this.itemIds[c]);
                        if(pos < 0)
                        {
                            unknowns++;
                            if(oldItemIds.length < unknowns)
                            {
                                tempArr[i][c] = this.ratings[i][c-unknowns];
                            }
                        }
                        else
                        {
                            tempArr[i][c] = this.ratings[i][pos];    
                        }
                    }
                }
            }
        }
        this.ratings = tempArr;
        if(shouldUpdateOneSlope)
        {
            this.createAndFillOneSlope();
        }
    }
    
    /*
     * Removes an item from the Rating table and rebuilds the One Slope table
     */
    public void removeItem(UserPreferences pref)
    {        
        // Get position in array
        int pos = Arrays.binarySearch(this.userIds, pref.getUserId());
        if(pos >= 0) // Only if truly exists
        {
            int[] tempUserIds = new int[this.userIds.length -1];
            float[][] tempratingTable = new float[this.userIds.length-1][this.itemIds.length];
            // Copy all data before position
            for(int i = 0; i < pos; i++)
            {
                tempUserIds[i] = this.userIds[i];
                for(int c = 0; c < this.itemIds.length; c++)
                {
                    tempratingTable[i][c] = this.ratings[i][c];
                }
            }
            // Copy all data after position
            for(int i = pos+1; i < this.userIds.length; i++)
            {
                tempUserIds[i-1] = this.userIds[i];
                for(int c = 0; c < this.itemIds.length; c++)
                {
                    tempratingTable[i-1][c] = this.ratings[i][c];
                }
            }

            this.userIds = tempUserIds;
            this.ratings = tempratingTable;
            this.createAndFillOneSlope();       
        }
    }
    
    /*
     * Prints all data. For debugging. FTW
     */
    public void updateItem(UserPreferences pref)
    {
        int[] oldItemIds = Arrays.copyOf(this.itemIds, this.itemIds.length);
        int[] tmpItemIds = new int[0];
        // Update ItemIds
        for(int c = 0; c < pref.getItemIds().length; c++)
        {
            tmpItemIds = ArrayResize.addItem(pref.getItemIds()[c], tmpItemIds);
        }
        
        // Get position in array
        int pos = Arrays.binarySearch(this.userIds, pref.getUserId());
        if(pos >= 0) // Only if truly exists
        {
            if(oldItemIds.length == tmpItemIds.length) // Only update lazy if no change to itemIds
            {
                // Update ONLY specified user
                for(int i = 0; i < pref.getItemIds().length; i++)
                {
                    int posItemId = Arrays.binarySearch(this.itemIds, pref.getItemIds()[i]);
                    this.ratings[pos][posItemId] = pref.getRatings()[i];
                }
            }
            else 
            {
                this.removeItem(pref);
                this.addItem(pref, false);
            }
        }
        this.createAndFillOneSlope();
    }
    
    /*
     * Prints all data. For debugging. FTW
     */
    public void printALl()
    {        
        System.out.println("------- RATING TABLE -------");
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
        System.out.println("------- ONE SLOPE -------");
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
