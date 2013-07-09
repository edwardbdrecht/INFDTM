/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itemitem;

import datamining.UserPreferences;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author Edward
 */
public class SlopeOneTest {

    public TreeMap<Integer, UserPreferences> userPreferences;

    public void testSlopeOne() {
        //userPreferences = new TreeMap<Integer, UserPreferences>();

        /*
         * A small set of users 
         */
        // A user
        /*
         UserPreferences u1 = new UserPreferences(101);
         u1.addElement(101, 5.0f);
         u1.addElement(102, 3.0f);
         u1.addElement(103, 2.5f);
         //u1.addElement(5, 1.0f);
         //u1.addElement(7, 3.0f);
         //u1.addElement(9, 5.0f);
         userPreferences.put(101, u1);

         // A user
         UserPreferences u2 = new UserPreferences(102);
         u2.addElement(101, 2.0f);
         u2.addElement(102, 2.5f);
         u2.addElement(103, 5.0f);
         u2.addElement(104, 2.0f);
         //u2.addElement(8, 5.0f);
         //u2.addElement(10, 2.3f);
         userPreferences.put(102, u2);

         // A user
         UserPreferences u3 = new UserPreferences(103);
         u3.addElement(101, 2.5f);
         u3.addElement(104, 4.0f);
         u3.addElement(105, 4.5f);
         u3.addElement(107, 5.0f);
         //u3.addElement(9, 5.0f);
         //u3.addElement(11, 1.5f);
         userPreferences.put(103, u3);

         // A user
         UserPreferences u4 = new UserPreferences(105);
         u4.addElement(101, 5.0f);
         u4.addElement(103, 3.0f);
         u4.addElement(104, 4.5f);
         u4.addElement(106, 4.0f);
         //u4.addElement(12, 4.8f);
         //u4.addElement(13, 3.0f);
         userPreferences.put(105, u4);

         // A user
         UserPreferences u5 = new UserPreferences(106);
         u5.addElement(101, 4.0f);
         u5.addElement(102, 3.0f);
         u5.addElement(103, 2.0f);
         u5.addElement(104, 4.0f);
         u5.addElement(105, 3.5f);
         u5.addElement(106, 4.0f);
         userPreferences.put(106, u5);*/

        /*
         * Generate massive amounts of random data
         *
         // Total users
         for(int p = 0; p < 10000; p++)
         {
         if(!userPreferences.containsKey(p))
         {
         UserPreferences u = new UserPreferences(p);
         int Low = 2;
         int High = 100;
         Random r = new Random();
         int R = r.nextInt(High-Low) + Low;
                
         // Random number of item ratings per user between Low & High
         for(int e = 0; e < R; e++)
         {
         // ItemID between Low2 and High2
         int Low2 = 1;
         int High2 = 110;
         Random r2 = new Random();
         int R2 = r2.nextInt(High2-Low2) + Low2;
                    
         //Rating between LowF and HighF
         float LowF = 0.0f;
         float HighF = 5.0f;
         Random rF = new Random();
         float RF = rF.nextFloat() * (HighF - LowF) + LowF;
         u.addElement(R2, RF);
         }
         userPreferences.put(p, u);
         }
         } */

        /*
         * Build ItemItem with given users
         */
        ItemItem item = new ItemItem();
        item.buildRatingTable(userPreferences);
        System.out.println("Starting OneSlope creation");
        item.createAndFillOneSlope();
        item.printALl();

        /*
         * Add a user
         *
         System.out.println("------- ADDING A USER -------");
         UserPreferences u6 = new UserPreferences(6);
         u6.addElement(101, 1.0f);
         u6.addElement(102, 2.0f);
         u6.addElement(103, 3.0f);
         u6.addElement(104, 4.0f);
         u6.addElement(105, 4.5f);
         u6.addElement(110, 5.0f);
         item.addItem(u6,true);
        
         // Another user
         UserPreferences u7 = new UserPreferences(7);
         u7.addElement(101, 2.0f);
         u7.addElement(102, 4.2f);
         u7.addElement(103, 1.4f);
         u7.addElement(104, 3.1f);
         u7.addElement(105, 3.5f);
         u7.addElement(108, 2.0f);
         item.addItem(u7,true);
       
         item.printALl();
         */

        /*
         * Update a user
         *
         System.out.println("------- UPDATE SAME USER -------");
         u6.addElement(120, 2.9f);
         item.updateItem(u6);
         item.printALl();
         */

        /*
         * Remove a user
         *
         System.out.println("------- REMOVE SAME USER -------");
         item.removeItem(u6);
         item.printALl();
         * /

         /*
         * Get ItemItem based recommendation for specified user
         */
        int userIdToTest = 5;
        System.out.println("Starting recommendation");
        long before = System.currentTimeMillis();
        RecommendationResult[] r = item.getRecommendation(userIdToTest);
        long after = System.currentTimeMillis();
        long diff = after - before;
        System.out.println("Used time: " + diff + "ms (" + after + " - " + before + ")");

        /*
         * Print recommendation
         */
        System.out.println("------- ITEMITEM RECOMMENDATION FOR USER " + userIdToTest + " -------");
        for (int i = 0; i < r.length; i++) {
            System.out.print((i + 1) + ". Item: " + r[i].getItemId() + " Value: " + r[i].getRecomValue());
            System.out.println(" ");
        }
    }
}
