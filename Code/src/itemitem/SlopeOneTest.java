/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itemitem;

import datamining.UserPreferences;
import java.util.TreeMap;

/**
 *
 * @author Edward
 */
public class SlopeOneTest {
    static TreeMap<Integer, UserPreferences> userPreferences;
    public void testSlopeOne()
    {
        userPreferences = new TreeMap<Integer, UserPreferences>();

        // A user
        UserPreferences u1 = new UserPreferences(101);
        u1.addElement(1, 5.0f);
        u1.addElement(2, 3.0f);
        u1.addElement(3, 2.5f);
        //u1.addElement(5, 1.0f);
        //u1.addElement(7, 3.0f);
        //u1.addElement(9, 5.0f);
        userPreferences.put(101, u1);

        // A user
        UserPreferences u2 = new UserPreferences(102);
        u2.addElement(1, 2.0f);
        u2.addElement(2, 2.5f);
        u2.addElement(3, 5.0f);
        u2.addElement(4, 2.0f);
        //u2.addElement(8, 5.0f);
        //u2.addElement(10, 2.3f);
        userPreferences.put(102, u2);

        // A user
        UserPreferences u3 = new UserPreferences(103);
        u3.addElement(1, 2.5f);
        u3.addElement(4, 4.0f);
        u3.addElement(5, 4.5f);
        u3.addElement(7, 5.0f);
        //u3.addElement(9, 5.0f);
        //u3.addElement(11, 1.5f);
        userPreferences.put(103, u3);

        // A user
        UserPreferences u4 = new UserPreferences(104);
        u4.addElement(1, 5.0f);
        u4.addElement(3, 3.0f);
        u4.addElement(4, 4.5f);
        u4.addElement(6, 4.0f);
        //u4.addElement(12, 4.8f);
        //u4.addElement(13, 3.0f);
        userPreferences.put(104, u4);

        // A user
        UserPreferences u5 = new UserPreferences(105);
        u5.addElement(1, 4.0f);
        u5.addElement(2, 3.0f);
        u5.addElement(3, 2.0f);
        u5.addElement(4, 4.0f);
        u5.addElement(5, 3.5f);
        u5.addElement(6, 4.0f);
        userPreferences.put(105, u5);
        /*
        // A user
        UserPreferences u6 = new UserPreferences(6);
        u6.addElement(1, 3.0f);
        u6.addElement(4, 5.0f);
        u6.addElement(5, 2.4f);
        //u6.addElement(6, 1.0f);
        //u6.addElement(7, 3.0f);
        //u6.addElement(8, 5.0f);
        userPreferences.put(6, u6);
        */
        // Test ItemItem
        ItemItem item = new ItemItem();
        item.buildRatingTable(userPreferences);
        item.printALl();
        item.getRecommendation(103);
    }
}
