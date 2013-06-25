/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itemitem;

/**
 *
 * @author Edward
 */
public class RecommendationResult implements Comparable {
    private int itemId;
    private float recomValue;

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the recomValue
     */
    public float getRecomValue() {
        return recomValue;
    }

    /**
     * @param recomValue the recomValue to set
     */
    public void setRecomValue(float recomValue) {
        this.recomValue = recomValue;
    }

    @Override
    public int compareTo(Object o) {
        RecommendationResult b = (RecommendationResult) o;
        if(b.getRecomValue() <  this.getRecomValue())
            return -1;
        else if(b.getRecomValue() >  this.getRecomValue())
            return 1;
        else
            return 0;
    }
    
    
}
