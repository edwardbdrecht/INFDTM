/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import itemitem.RecommendationResult;
import java.util.Arrays;

/**
 *
 * @author Edward
 */
public class ArrayResize {
    
    /*
     * Adds an item into an array at its proper position
     */
    public static int[] addItem(int id, int[] sourceArr)
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
    public static float[] addItem(float id, float[] sourceArr)
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
     * Adds an item into an array at its proper position
     */
    public static RecommendationResult[] addItem(RecommendationResult id, RecommendationResult[] sourceArr)
    {        
        int key = Arrays.binarySearch(sourceArr, id); //search in the itemId's array for a corresponding id.
        if (key >= 0) { //this item already exists. Just overwrite the ratings value now
                sourceArr[key] = id;
        }
        else { //new itemId
                key = Math.abs(key)-1; //define the spot in the array for the new item

                //get both arrays with 1 extra space for the new item
                RecommendationResult[] itemIdsCopy = Arrays.copyOf(sourceArr, sourceArr.length+1);

                //check if the position of the new item is at the end or not
                if(key+1 < itemIdsCopy.length) { //position is not at the end so we need to make space for the new element
                        System.arraycopy(sourceArr, key, itemIdsCopy, key+1, itemIdsCopy.length-key-1);
                }

                itemIdsCopy[key] = id;
                return itemIdsCopy;
        }
        return sourceArr;
    }
    
    /*public static float[][] addItem(int xindex, int yindex, )
    {
        
    }*/
}
