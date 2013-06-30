package datamining;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Wayne Rijsdijk
 */
public class TopProducts {
	HashMap<String,Float> map = new HashMap<String,Float>();
	ValueComparator bvc =  new ValueComparator(map);
	TreeMap<String,Float> sorted_map = new TreeMap<String,Float>(bvc);
	
	//Map<Integer, Integer> products = new TreeMap<Integer, Integer>();
	
	public void addProduct(int itemId, float rating) {
		String key = String.valueOf(itemId);
		float productCount = this.map.containsKey(key) ? this.map.get(key) : 0;
		if(productCount <= 0)
			this.map.put(key, rating);
	}
	
	public String[] getPopularProducts() {
		return this.getPopularProducts(10);
	}
	
	public String[] getPopularProducts(int limit) {
		String[] itemIds = new String[limit];
		
		sorted_map.putAll(map);
		Set<String> ks = sorted_map.keySet();
		Iterator it = ks.iterator();
		int counter = 0;
		while (it.hasNext()) {
			itemIds[counter] = (String) it.next();
			
			
			//System.out.println(pairs.getKey() + " = " + pairs.getValue());
			it.remove(); // avoids a ConcurrentModificationException
			
			counter++;
			if(counter >= 3) break;
		}
 
		return Arrays.copyOf(itemIds, counter);
	}
}

class ValueComparator implements Comparator<String> {

    Map<String, Float> base;
    public ValueComparator(Map<String, Float> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}